package com.rochanahuel.orders_service.dto;

import java.util.List;

public record ModifyOrderRequest(
        Long idOrder,

        List<OrderItemsRequest> orderItems
) {
}
