package com.rochanahuel.orders_service.dto;

public record OrderItemsRequest(
        String sku,
        Double price,
        Long quantity
) {
}
