package com.rochanahuel.products_service.dto;

public record RemoveStockRequest(
        Long quantity,
        String sku
) {
}
