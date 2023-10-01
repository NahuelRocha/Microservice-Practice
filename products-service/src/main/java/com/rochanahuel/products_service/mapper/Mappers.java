package com.rochanahuel.products_service.mapper;

import com.rochanahuel.products_service.dto.AllProducts;
import com.rochanahuel.products_service.dto.ProductDto;
import com.rochanahuel.products_service.model.Product;
import org.springframework.stereotype.Component;


@Component
public class Mappers {

    public ProductDto productToProductDto(Product product) {

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory()
        );
    }

    public AllProducts productToAllProduct(Product product){

        return AllProducts.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sku(product.getSku())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
