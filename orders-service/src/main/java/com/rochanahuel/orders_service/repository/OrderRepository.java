package com.rochanahuel.orders_service.repository;

import com.rochanahuel.orders_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByEmailOfUserOrder(String emailOfUserOrder);


}
