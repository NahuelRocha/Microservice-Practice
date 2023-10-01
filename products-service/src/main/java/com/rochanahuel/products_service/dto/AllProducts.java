package com.rochanahuel.products_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllProducts implements Serializable {

    private Long id;

    private String name;

    private String sku;

    private String description;

    private Integer price;

    private Long stock;
}
