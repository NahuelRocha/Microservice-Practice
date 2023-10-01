package com.rochanahuel.orders_service.controller;

import com.rochanahuel.orders_service.dto.DeleteOrderItemRequest;
import com.rochanahuel.orders_service.dto.OrderDto;
import com.rochanahuel.orders_service.dto.OrderRequest;
import com.rochanahuel.orders_service.dto.AddProductToOrderRequest;
import com.rochanahuel.orders_service.service.impl.OrderServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping
    @CircuitBreaker(name = "orders-service" , fallbackMethod = "placeOrderFallback")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }

    private ResponseEntity<String> placeOrderFallback(OrderRequest orderRequest , Throwable throwable) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {

        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @GetMapping("/all-orders/{email}")
    public ResponseEntity<List<OrderDto>> findOrderByUsername(@PathVariable("email") String email) {

        return ResponseEntity.ok(orderService.findOrderByUsername(email));
    }

    @PutMapping("/delete-item")
    public ResponseEntity<OrderDto> deleteOrderItemInOrder(@RequestBody DeleteOrderItemRequest deleteOrderItemRequest) {

        return ResponseEntity.ok(orderService.deleteOrderItemInOrder(deleteOrderItemRequest));
    }

    @PutMapping("/add-products")
    public ResponseEntity<OrderDto> addProductsToOrderItems(@RequestBody AddProductToOrderRequest addProductToOrderRequest) {

        return ResponseEntity.ok(orderService.addProductsToOrderItems(addProductToOrderRequest));
    }
}
