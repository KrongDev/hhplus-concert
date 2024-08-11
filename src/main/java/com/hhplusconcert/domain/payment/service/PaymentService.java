package com.hhplusconcert.domain.payment.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {
    //
    private final PaymentRepository paymentRepository;

    @Transactional
    public String create(
            String userId,
            int price
    ) {
        //
        Payment payment = Payment.newInstance(userId, price);
        this.paymentRepository.save(payment);
        return payment.getPaymentId();
    }

    public Payment loadPayment(String paymentId) {
        //
        Payment payment = this.paymentRepository.loadPaymentById(paymentId);
        if(Objects.isNull(payment))
            throw new CustomGlobalException(ErrorType.PAYMENT_NOT_FOUND);
        return payment;
    }

    public List<Payment> loadPaymentsByUserId(String userId) {
        //
        return this.paymentRepository.loadPaymentsByUserId(userId);
    }
}
