package com.hab.ecommerce.order;

import com.hab.ecommerce.customer.CustomerClient;
import com.hab.ecommerce.exception.BusinessException;
import com.hab.ecommerce.kafka.OrderConfirmation;
import com.hab.ecommerce.kafka.OrderProducer;
import com.hab.ecommerce.orderline.OrderLineRequest;
import com.hab.ecommerce.orderline.OrderLineService;
import com.hab.ecommerce.payment.PaymentClient;
import com.hab.ecommerce.payment.PaymentRequest;
import com.hab.ecommerce.product.ProductClient;
import com.hab.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        // check the customer --> OpenFeign
        var customer = customerClient.findCustomerById(request.customer_id())
                .orElseThrow(() -> new BusinessException(
                        "Can not create order:: No customer exits")
                );
        // purchase the product -> product-ms (RestTemplate)
        var purchasedProducts = productClient.purchaseProduct(request.products());

        // persist order
        var order = repository.save(mapper.toOrder(request));

        // persist order line
        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                OrderLineRequest.builder()
                    .id(null)
                    .orderId(order.getId())
                    .productId(purchaseRequest.productId())
                    .quantity(purchaseRequest.quantity())
                    .build()
            );
        }
        // start payment process
        paymentClient.requestOrderPayment(
            PaymentRequest.builder()
                .id(null)
                .orderId(order.getId())
                .orderReference(order.getReference())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .customer(customer)
                .build()
        );

        // send order confirmation to notification -> ms (kafka)
        orderProducer.sendOrderConfirmation(
                OrderConfirmation.builder()
                        .orderReference(request.reference())
                        .totalAmount(request.amount())
                        .paymentMethod(request.paymentMethod())
                        .customer(customer)
                        .products(purchasedProducts)
                        .build()
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
      return   repository.findAll().stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return  repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "No order found with the provided ID:: %d", orderId)
                ));
    }
}
