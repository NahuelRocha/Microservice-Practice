package com.rochanahuel.orders_service.service;

import com.rochanahuel.orders_service.dto.DeleteOrderItemRequest;
import com.rochanahuel.orders_service.dto.OrderDto;
import com.rochanahuel.orders_service.dto.OrderRequest;
import com.rochanahuel.orders_service.dto.AddProductToOrderRequest;

import java.util.List;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);

    String deleteOrder(Long id);

    List<OrderDto> findOrderByUsername(String email);

    OrderDto deleteOrderItemInOrder(DeleteOrderItemRequest deleteOrderItemRequest);

    OrderDto addProductsToOrderItems(AddProductToOrderRequest addProductToOrderRequest);

}
