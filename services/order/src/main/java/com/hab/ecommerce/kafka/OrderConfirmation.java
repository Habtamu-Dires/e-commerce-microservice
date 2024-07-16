package com.hab.ecommerce.kafka;

import com.hab.ecommerce.customer.Customer;
import com.hab.ecommerce.order.PaymentMethod;
import com.hab.ecommerce.product.PurchaseResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<PurchaseResponse> products
) {
}
