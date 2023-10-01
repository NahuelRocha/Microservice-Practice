package com.rochanahuel.orders_service.service.impl;

import com.rochanahuel.orders_service.dto.*;
import com.rochanahuel.orders_service.exception.OrderNotFoundException;
import com.rochanahuel.orders_service.exception.OrderProcessingException;
import com.rochanahuel.orders_service.mapper.Mapper;
import com.rochanahuel.orders_service.model.Order;
import com.rochanahuel.orders_service.model.OrderItems;
import com.rochanahuel.orders_service.repository.OrderItemsRepository;
import com.rochanahuel.orders_service.repository.OrderRepository;
import com.rochanahuel.orders_service.service.OrderService;
import com.rochanahuel.orders_service.utils.JsonUtils;
import com.rochanahuel.orders_service.utils.OrderStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final HttpServletRequest httpServletRequest;
    private final OrderRepository orderRepository;
    private final Mapper mapper;
    private final WebClient.Builder webClient;
    private final OrderItemsRepository orderItemsRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public String placeOrder(OrderRequest orderRequest) {

        final String getHeader = httpServletRequest.getHeader("Authorization");
        final String token = getHeader.substring(7);

        BaseResponse result = webClient.build()
                .post()
                .uri("lb://products-service/api/product/in-stock")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .bodyValue(orderRequest.orderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();


        if (result != null && !result.hasErrors()) {

            Order newOrder = new Order();

            newOrder.setEmailOfUserOrder(orderRequest.email());

            newOrder.setOrderNumber(UUID.randomUUID().toString());

            newOrder.setOrderItems(orderRequest.orderItems().stream()
                    .map(orderItemsRequest -> mapper.orderRequestToOrderItems(orderItemsRequest, newOrder))
                    .collect(Collectors.toList()));

            newOrder.setTotalPrice(orderRequest.orderItems().stream()
                    .map(mapper::getTotalPrice)
                    .reduce(0.0, Double::sum));

            kafkaTemplate.send("orders-topic", JsonUtils.toJson(new OrderEvent(
                    newOrder.getOrderNumber(),
                    newOrder.getOrderItems().size(),
                    OrderStatus.PLACED)));

            this.orderRepository.save(newOrder);

            return "SUCCESS";

        } else {

            throw new OrderProcessingException("Insufficient stock");
        }
    }

    public String deleteOrder(Long id) {

        Order deleteOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        orderRepository.delete(deleteOrder);

        return "DELETED ORDER";
    }

    public List<OrderDto> findOrderByUsername(String email) {

        List<Order> findOrders = orderRepository.findByEmailOfUserOrder(email);

        if (findOrders.isEmpty()) throw new OrderNotFoundException("Not orders found with user email: " + email);

        return findOrders.stream().map(mapper::orderToOrderDto).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto deleteOrderItemInOrder(DeleteOrderItemRequest deleteOrderItemRequest) {

        OrderItems findOrder = orderItemsRepository.findById(deleteOrderItemRequest.id())
                .orElseThrow(() -> new OrderNotFoundException("OrderItem not found with id: " + deleteOrderItemRequest.id()));

        Order order = orderRepository.findById(findOrder.getOrder().getId())
                .orElseThrow(() -> new OrderNotFoundException("OrderItem not found with id: " + findOrder.getOrder().getId()));

        Double totalPriceChange = findOrder.getPrice() * findOrder.getQuantity();

        order.setTotalPrice(order.getTotalPrice() - totalPriceChange);

        orderRepository.save(order);

        orderItemsRepository.delete(findOrder);

        final String getHeader = httpServletRequest.getHeader("Authorization");
        final String token = getHeader.substring(7);

        ProductDto result = webClient.build()
                .put()
                .uri("lb://products-service/api/product/add-stock")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .bodyValue(deleteOrderItemRequest)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();

        if (result != null) {
            return mapper.orderToOrderDto(order);

        } else {
            throw new OrderProcessingException("Could not update the stock");
        }

    }

    @Override
    public OrderDto addProductsToOrderItems(AddProductToOrderRequest addProductToOrderRequest) {

        final String getHeader = httpServletRequest.getHeader("Authorization");
        final String token = getHeader.substring(7);

        String result = webClient.build()
                .put()
                .uri("lb://products-service/api/product/remove-stock")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .bodyValue(addProductToOrderRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (result != null && result.equals("SUCCESS")) {

            OrderItems findOrderItems = orderItemsRepository.findById(addProductToOrderRequest.id())
                    .orElseThrow(() -> new OrderNotFoundException("OrderItems not found"));

            Long newQuantity = findOrderItems.getQuantity() + addProductToOrderRequest.quantity();

            findOrderItems.setQuantity(newQuantity);

            orderItemsRepository.save(findOrderItems);

            Order actOrder = orderRepository.findById(findOrderItems.getOrder().getId())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found."));

            actOrder.setTotalPrice(actOrder.getTotalPrice() + (addProductToOrderRequest.quantity() * findOrderItems.getPrice()));

            orderRepository.save(actOrder);

            return mapper.orderToOrderDto(actOrder);

        } else {
            throw new OrderProcessingException("Order processing error.");
        }

    }

}
