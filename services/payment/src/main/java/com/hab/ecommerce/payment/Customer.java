package com.hab.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated // the attribute can be validated , it called from the class that is validate
public record Customer(
    String id,
    @NotNull(message = "First Name is required")
    String firstname,
    @NotNull(message = "Last Name is required")
    String lastname,
    @NotNull(message = "Email is required")
    @Email(message = "Email is not correctly  formated")
    String email
) { }
