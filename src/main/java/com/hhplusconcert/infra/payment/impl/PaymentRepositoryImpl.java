package com.hhplusconcert.infra.payment.impl;

import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.domain.payment.repository.PaymentRepository;
import com.hhplusconcert.infra.payment.orm.PaymentJpaRepository;
import com.hhplusconcert.infra.payment.orm.jpo.PaymentJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    //
    private final PaymentJpaRepository paymentJpaRepository;

    public void save(Payment payment) {
        //
        this.paymentJpaRepository.save(new PaymentJpo(payment));
    }

    public Payment loadPaymentById(String paymentId) {
        //
        Optional<PaymentJpo> jpo = this.paymentJpaRepository.findById(paymentId);
        return jpo.map(PaymentJpo::toDomain).orElse(null);
    }

    public List<Payment> loadPaymentsByUserId(String userId) {
        //
        List<PaymentJpo> jpos = this.paymentJpaRepository.findAllByUserId(userId);
        return jpos.stream().map(PaymentJpo::toDomain).toList();
    }
}
