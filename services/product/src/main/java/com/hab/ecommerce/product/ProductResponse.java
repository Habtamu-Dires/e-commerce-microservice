package com.hab.ecommerce.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        double availableQuantity,
        Integer category_id,
        String category_name,
        String category_description
) {
}
