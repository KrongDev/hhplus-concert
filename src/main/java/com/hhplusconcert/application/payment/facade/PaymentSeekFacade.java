package com.hhplusconcert.application.payment.facade;

import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentSeekFacade {
    //
    private final PaymentService paymentService;

    public Payment loadPayment(String paymentId) {
        //
        return this.paymentService.loadPayment(paymentId);
    }

    public List<Payment> loadPayments(String userId) {
        //
        return this.paymentService.loadPaymentsByUserId(userId);
    }
}
