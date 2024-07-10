package com.hhplusconcert.infra.payment.impl;

import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.domain.payment.repository.PaymentRepository;
import com.hhplusconcert.infra.payment.orm.PaymentJpoRepository;
import com.hhplusconcert.infra.payment.orm.jpo.PaymentJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    //
    private final PaymentJpoRepository paymentJpoRepository;

    public void save(Payment payment) {
        //
        this.paymentJpoRepository.save(new PaymentJpo(payment));
    }

    public Payment loadPaymentWithThrow(String paymentId) {
        //
        PaymentJpo jpo = this.paymentJpoRepository.findById(paymentId).orElseThrow();
        return jpo.toDomain();
    }

    public List<Payment> loadPaymentsByUserId(String userId) {
        //
        List<PaymentJpo> jpos = this.paymentJpoRepository.findAllByUserId(userId);
        return jpos.stream().map(PaymentJpo::toDomain).toList();
    }
}
