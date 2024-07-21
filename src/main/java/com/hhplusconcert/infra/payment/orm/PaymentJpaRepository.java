package com.hhplusconcert.infra.payment.orm;

import com.hhplusconcert.infra.payment.orm.jpo.PaymentJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpo, String> {
    //
    List<PaymentJpo> findAllByUserId(String userId);
}
