package com.hab.ecommerce.customer;

public record Customer(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
