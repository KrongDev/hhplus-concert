package com.hhplusconcert.domain.payment.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Payment {
    private String paymentId;
    private String userId;
    private int price;
    private Long createAt;

    public static Payment newInstance(
            String userId,
            int price
    ) {
        String newId = UUID.randomUUID().toString();
        return Payment.builder()
                .paymentId(newId)
                .userId(userId)
                .price(price)
                .createAt(System.currentTimeMillis())
                .build();
    }
}
