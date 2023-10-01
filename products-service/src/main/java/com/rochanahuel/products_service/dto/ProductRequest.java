package com.rochanahuel.products_service.dto;

import com.rochanahuel.products_service.utils.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotEmpty(message = "Name cannot be null")
        String name,
        @NotEmpty(message = "Sku cannot be null")
        String sku,

        @NotEmpty(message = "Description cannot be null")
        String description,

        @NotNull(message = "Price cannot be null")
        Integer price,

        @NotNull(message = "Stock cannot be null")
        Long stock,

        Category category
) {
}
