package com.rochanahuel.products_service.dto;

public record OrderItemsRequest(
        Long id,
        String sku,
        Double price,
        Long quantity
) {
}
