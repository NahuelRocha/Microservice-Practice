package com.rochanahuel.orders_service.dto;

public record DeleteOrderItemRequest(
        Long id,
        Long quantity,
        String sku
) {
}
