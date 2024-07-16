package com.hab.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
  private final CustomerService service;

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> findAll(){
    return ResponseEntity.ok(service.findAllCustomers());
  }

  @GetMapping("/exists/{customer_id}")
  public ResponseEntity<Boolean> existsById(
          @PathVariable("customer_id") String customerId
  ){
      return ResponseEntity.ok(service.existsById(customerId));
  }


  @GetMapping("/{customer_id}")
  public ResponseEntity<CustomerResponse> findByById(
          @PathVariable("customer_id") String customerId
  ){
    return ResponseEntity.ok(service.findById(customerId));
  }

  @PostMapping
  public ResponseEntity<String> createCustomer(
      @RequestBody @Valid CustomerRequest customerRequest
  ) {
    return ResponseEntity.ok(service.createCustomer(customerRequest));
  }

  @PutMapping
  public ResponseEntity<?> updateCustomer(
          @RequestBody @Valid CustomerRequest request
  ){
       service.updateCustomer(request);
       return ResponseEntity.accepted().build();
  }

  @DeleteMapping("/{customer_id}")
  public ResponseEntity<Void> delete(
          @PathVariable("customer_id") String customerId
  ){
    service.deleteCustomer(customerId);
    return ResponseEntity.accepted().build();
  }
}
