package com.hab.ecommerce.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {}
