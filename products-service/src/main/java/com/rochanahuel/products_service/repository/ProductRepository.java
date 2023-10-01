package com.rochanahuel.products_service.repository;

import com.rochanahuel.products_service.model.Product;
import com.rochanahuel.products_service.utils.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    Optional<Product> findBySku(String sku);

    List<Product> findBySkuIn(List<String> sku);
}
