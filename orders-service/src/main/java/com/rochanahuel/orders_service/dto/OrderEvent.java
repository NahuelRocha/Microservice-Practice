package com.rochanahuel.orders_service.dto;

import com.rochanahuel.orders_service.utils.OrderStatus;

public record OrderEvent(
        String orderNumber,
        int itemsCount,
        OrderStatus orderStatus
) {
}
