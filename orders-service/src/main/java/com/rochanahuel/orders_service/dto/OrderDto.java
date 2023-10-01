package com.rochanahuel.orders_service.dto;

import com.rochanahuel.orders_service.model.OrderItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public record OrderDto(
             Long id,

             String orderNumber,

             String emailOfUserOrder,

             Double totalPrice

) {
}
