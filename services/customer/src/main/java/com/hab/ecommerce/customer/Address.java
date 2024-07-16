package com.hab.ecommerce.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {

    private String street;
    private String houseNumber;
    @NotNull(message = "ZipCode should not be null")
    @NotEmpty(message = "zipCode should not be empty")
    @NotBlank(message = "zipcode should not be blank")
    private String zipCode;
}
