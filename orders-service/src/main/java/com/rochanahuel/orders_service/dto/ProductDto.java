package com.rochanahuel.orders_service.dto;

import com.rochanahuel.orders_service.utils.Category;

public record ProductDto(
        Long id,

        String name,

        String sku,

        String description,

        Integer price,

        Long stock,

        Category category
) {
}
