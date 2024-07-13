package com.hhplusconcert.domain.payment.service;

import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    //
    private final PaymentRepository paymentRepository;

    @Transactional
    public String create(
            String reservationId,
            String userId,
            int price
    ) {
        //
        Payment payment = Payment.newInstance(reservationId, userId, price);
        this.paymentRepository.save(payment);
        return payment.getPaymentId();
    }

    public Payment loadPayment(String paymentId) {
        //
        return this.paymentRepository.loadPaymentWithThrow(paymentId);
    }

    public List<Payment> loadPaymentsByUserId(String userId) {
        //
        return this.paymentRepository.loadPaymentsByUserId(userId);
    }
}
