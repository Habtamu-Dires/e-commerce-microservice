package com.hab.ecommerce.customer;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message="Customer firstName is required")
        String firstname,
        @NotNull(message="Customer lastName is required")
        String lastname,
        @NotNull(message="Customer email is required")
        @Email(message = "Customer email address is not valid email address")
        String email,
        @NotNull(message = "Address should not be null")
        @Valid Address address
) {
}
