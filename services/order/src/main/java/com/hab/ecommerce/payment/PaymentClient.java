package com.hab.ecommerce.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service"
        //url="${application.config.via-gateway.payment-url}"
)
public interface PaymentClient {

     @Value("${application.config.payment-url}")
     String url = "/api/v1/payments";

    @PostMapping(url)
    Integer requestOrderPayment(
            @RequestBody PaymentRequest request
    );
}
