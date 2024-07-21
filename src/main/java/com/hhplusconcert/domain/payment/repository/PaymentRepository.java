package com.hhplusconcert.domain.payment.repository;

import com.hhplusconcert.domain.payment.model.Payment;

import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);
    Payment loadPaymentById(String paymentId);
    List<Payment> loadPaymentsByUserId(String userId);
}
