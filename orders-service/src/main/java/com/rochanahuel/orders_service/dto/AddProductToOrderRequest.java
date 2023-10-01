package com.rochanahuel.orders_service.dto;

public record AddProductToOrderRequest(
        Long id,
        Long quantity,
        String sku
) {
}
