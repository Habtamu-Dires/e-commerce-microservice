package com.hab.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<OrderResponse> findById(
            @PathVariable("order_id") Integer orderId
    ){
        return ResponseEntity.ok(service.findById(orderId));
    }

    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest request
    ){
        return ResponseEntity.ok(service.createOrder(request));
    }
}
