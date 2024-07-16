package com.hab.ecommerce.payment;

import com.hab.ecommerce.customer.Customer;
import com.hab.ecommerce.order.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {}
