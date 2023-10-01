package com.rochanahuel.orders_service.mapper;

import com.rochanahuel.orders_service.dto.OrderDto;
import com.rochanahuel.orders_service.dto.OrderItemsRequest;
import com.rochanahuel.orders_service.model.Order;
import com.rochanahuel.orders_service.model.OrderItems;
import org.springframework.stereotype.Component;


@Component
public class Mapper {
    public OrderItems orderRequestToOrderItems(OrderItemsRequest orderItems, Order order) {

        return OrderItems.builder()
                .sku(orderItems.sku())
                .price(orderItems.price())
                .quantity(orderItems.quantity())
                .order(order)
                .build();
    }

    public Double getTotalPrice(OrderItemsRequest order){

        return order.price() * order.quantity();
    }

    public OrderDto orderToOrderDto(Order order){

        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getEmailOfUserOrder(),
                order.getTotalPrice()
        );
    }

//    public OrderResponse orderToOrderResponse(Order order) {
//
//
//        return new OrderResponse(
//                order.getId(),
//                order.getOrderNumber(),
//                order.getOrderItems()
//                        .stream()
//                        .map(this::orderItemsToOrderResponse).collect(Collectors.toList())
//        );
//
//    }
//
//    public OrderItemsResponse orderItemsToOrderResponse(OrderItems orderItems) {
//
//        return new OrderItemsResponse(
//                orderItems.getId(),
//                orderItems.getSku(),
//                orderItems.getPrice(),
//                orderItems.getQuantity()
//        );
//    }
}
