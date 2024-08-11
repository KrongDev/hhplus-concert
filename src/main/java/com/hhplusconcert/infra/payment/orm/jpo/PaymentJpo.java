package com.hhplusconcert.infra.payment.orm.jpo;

import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.infra.common.JpoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentJpo implements JpoEntity<Payment> {
    //
    @Id
    private String paymentId;
    private String userId;
    private int price;
    private Long createAt;

    public PaymentJpo(Payment payment) {
        BeanUtils.copyProperties(payment, this);
    }

    @Override
    public Payment toDomain() {
        return Payment.builder()
                .paymentId(paymentId)
                .userId(userId)
                .price(price)
                .createAt(createAt)
                .build();
    }
}
