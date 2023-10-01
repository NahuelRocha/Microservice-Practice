package com.rochanahuel.orders_service.dto;

import java.util.List;

public record OrderRequest(

        String email,
        List<OrderItemsRequest> orderItems
) {
}
