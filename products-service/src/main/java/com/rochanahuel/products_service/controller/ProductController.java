package com.rochanahuel.products_service.controller;

import com.rochanahuel.products_service.dto.*;
import com.rochanahuel.products_service.model.Product;
import com.rochanahuel.products_service.service.impl.ProductServiceImpl;
import com.rochanahuel.products_service.utils.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/add")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductRequest product) {

        return ResponseEntity.ok(productService.addProduct(product));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {

        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {

        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> findAllByCategory(@PathVariable("category") Category category) {

        return ResponseEntity.ok(productService.findByCategory(category));
    }

    @GetMapping("/stock/{sku}")
    public ResponseEntity<Boolean> isInStock(@PathVariable("sku") String sku) {

        return ResponseEntity.ok(productService.isInStock(sku));
    }

    @PostMapping("/in-stock")
    public ResponseEntity<BaseResponse> areInStock(@RequestBody List<OrderItemsRequest> orderItems) {

        return ResponseEntity.ok(productService.areInStock(orderItems));
    }

    @PutMapping("/add-stock")
    public ResponseEntity<ProductDto> addStock(@RequestBody AddStockRequest addStockRequest) {

        return ResponseEntity.ok(productService.addStock(addStockRequest));
    }

    @PutMapping("/remove-stock")
    public ResponseEntity<String> removeStock(@RequestBody RemoveStockRequest removeStockRequest) {

        return ResponseEntity.ok(productService.removeStock(removeStockRequest));
    }

    @GetMapping("/all")
    @Cacheable("products")
    public List<AllProducts> getAllProducts() {

        return productService.getAllProducts();
    }


}
