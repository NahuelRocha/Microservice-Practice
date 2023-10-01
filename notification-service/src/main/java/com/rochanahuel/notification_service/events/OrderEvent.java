package com.rochanahuel.notification_service.events;


import com.rochanahuel.notification_service.utils.OrderStatus;

public record OrderEvent(
        String orderNumber,
        int itemsCount,
        OrderStatus orderStatus
) {
}
