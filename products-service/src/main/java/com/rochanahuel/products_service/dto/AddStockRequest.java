package com.rochanahuel.products_service.dto;

public record AddStockRequest(
        Long quantity,
        String sku
) {
}
