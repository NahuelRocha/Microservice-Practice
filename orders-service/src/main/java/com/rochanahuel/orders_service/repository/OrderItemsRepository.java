package com.rochanahuel.orders_service.repository;

import com.rochanahuel.orders_service.model.Order;
import com.rochanahuel.orders_service.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    OrderItems findBySku(String sku);
    List<OrderItems> findBySkuIn(List<String> skuList);
    List<OrderItems> findByOrder(Order order);
}
