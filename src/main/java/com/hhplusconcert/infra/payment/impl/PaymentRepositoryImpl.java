package com.hhplusconcert.infra.payment.impl;

import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.domain.payment.repository.PaymentRepository;
import com.hhplusconcert.infra.payment.orm.PaymentJpoRepository;
import com.hhplusconcert.infra.payment.orm.jpo.PaymentJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    //
    private final PaymentJpoRepository paymentJpoRepository;

    public void save(Payment payment) {
        //
        this.paymentJpoRepository.save(new PaymentJpo(payment));
    }

    public Payment loadPaymentById(String paymentId) {
        //
        Optional<PaymentJpo> jpo = this.paymentJpoRepository.findById(paymentId);
        return jpo.map(PaymentJpo::toDomain).orElse(null);
    }

    public List<Payment> loadPaymentsByUserId(String userId) {
        //
        List<PaymentJpo> jpos = this.paymentJpoRepository.findAllByUserId(userId);
        return jpos.stream().map(PaymentJpo::toDomain).toList();
    }
}
