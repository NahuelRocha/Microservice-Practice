package com.rochanahuel.products_service.service.impl;

import com.rochanahuel.products_service.dto.*;
import com.rochanahuel.products_service.exception.ProductNotFoundException;
import com.rochanahuel.products_service.mapper.Mappers;
import com.rochanahuel.products_service.model.Product;
import com.rochanahuel.products_service.repository.ProductRepository;
import com.rochanahuel.products_service.service.ProductService;
import com.rochanahuel.products_service.utils.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Mappers mappers;

    @Override
    public ProductDto addProduct(ProductRequest product) {

        Product newProduct = Product.builder()
                .name(product.name())
                .sku(product.sku())
                .description(product.description())
                .stock(product.stock())
                .price(product.price())
                .category(product.category())
                .build();

        productRepository.save(newProduct);

        return mappers.productToProductDto(newProduct);
    }

    @Override
    public ProductDto getProduct(Long id) {

        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        return mappers.productToProductDto(findProduct);
    }

    @Override
    public String deleteProduct(Long id) {

        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        productRepository.deleteById(id);

        return "SUCCESS";
    }

    @Override
    public List<ProductDto> findByCategory(Category category) {

        List<Product> findProducts = productRepository.findByCategory(category);

        if (findProducts.isEmpty()) throw new ProductNotFoundException("Products not found with category: " + category);

        return findProducts.stream()
                .map(mappers::productToProductDto)
                .collect(Collectors.toList());
    }

    public boolean isInStock(String sku) {

        var product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with sku: " + sku));

        return product.getStock() > 0;
    }

    public BaseResponse areInStock(List<OrderItemsRequest> orderItems) {

        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemsRequest::sku).toList();

        List<Product> productList = productRepository.findBySkuIn(skus);

        orderItems.forEach(orders -> {

            var product = productList.stream().filter(value -> value.getSku().equals(orders.sku())).findFirst();

            if (product.isEmpty()) {
                errorList.add("Product with sku " + orders.sku() + " does not exist");
            } else if (product.get().getStock() < orders.quantity()) {
                errorList.add("Product with sku " + orders.sku() + " has insufficient quantity");
            }
        });

        if (errorList.isEmpty()) {

            orderItems.forEach(orders -> {


                var product = productList.stream().filter(value -> value.getSku().equals(orders.sku())).findFirst();

                var productStock = product.get().getStock();

                product.get().setStock(productStock - orders.quantity());

                productRepository.save(product.get());
            });

            return new BaseResponse(null);

        } else {
            return new BaseResponse(errorList.toArray(new String[0]));
        }
    }

    @Override
    public ProductDto addStock(AddStockRequest addStockRequest) {

        Product findProduct = productRepository.findBySku(addStockRequest.sku())
                .orElseThrow(()-> new ProductNotFoundException("Product not found with sku: " + addStockRequest.sku()));

        findProduct.setStock(findProduct.getStock() + addStockRequest.quantity());

        productRepository.save(findProduct);

        return mappers.productToProductDto(findProduct);
    }

    @Override
    public String removeStock(RemoveStockRequest removeStockRequest) {

        Product findProduct = productRepository.findBySku(removeStockRequest.sku())
                .orElseThrow(()-> new ProductNotFoundException("Product not found."));

        if (findProduct.getStock() > removeStockRequest.quantity()){

            findProduct.setStock(findProduct.getStock() - removeStockRequest.quantity());
            productRepository.save(findProduct);

        } else throw new ProductNotFoundException("Not stock.");

        return "SUCCESS";
    }

    @Override
    public List<AllProducts> getAllProducts() {

        List<Product> findAll = productRepository.findAll();

        if (findAll.isEmpty()){
            throw new ProductNotFoundException("Not products in the database.");
        }

        return findAll.stream().map(mappers::productToAllProduct)
                .collect(Collectors.toList());
    }


}
