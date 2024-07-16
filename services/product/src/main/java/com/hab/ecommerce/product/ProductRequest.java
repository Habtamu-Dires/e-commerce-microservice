package com.hab.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Integer id,
         @NotNull(message = "Product name is required")
         String name,
         @NotNull(message = "Product description is required ")
         String description,
         @Positive(message = "Price should be positive")
         BigDecimal price,
         @Positive(message = "Available quantity should be positive")
         double availableQuantity,
         @NotNull(message = "Product category is required")
         Integer category_id
) {

}
