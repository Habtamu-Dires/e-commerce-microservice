package com.hab.ecommerce.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer-service"
        //url = "${application.config.via-gateway.customer-url}"
)
public interface CustomerClient {

    @Value("${application.config.customer-url:/api/v1/customers}")
    String url = "/api/v1/customers";

    @GetMapping(url+"/{customer_id}")
    Optional<Customer> findCustomerById(
            @PathVariable("customer_id") String customer_id
    );
}
