package com.rochanahuel.products_service.service;

import com.rochanahuel.products_service.dto.*;
import com.rochanahuel.products_service.model.Product;
import com.rochanahuel.products_service.utils.Category;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductRequest product);

    ProductDto getProduct(Long id);

    String deleteProduct(Long id);

    List<ProductDto> findByCategory(Category category);

    boolean isInStock(String sku);

    BaseResponse areInStock(List<OrderItemsRequest> orderItems);

    ProductDto addStock(AddStockRequest addStockRequest);

    String removeStock(RemoveStockRequest removeStockRequest);

    List<AllProducts> getAllProducts();

}
