package com.hab.ecommerce.payment;

import com.hab.ecommerce.notification.NotificationProducer;
import com.hab.ecommerce.notification.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));

        notificationProducer.sendNotification(
            PaymentConfirmation.builder()
                .orderReference(request.orderReference())
                .amount(request.amount())
                .customerEmail(request.customer().email())  //null not checked because it already validated.
                .customerFirstname(request.customer().firstname())
                .customerLastname(request.customer().lastname())
                .build()
        );
        return payment.getId();
    }
}
