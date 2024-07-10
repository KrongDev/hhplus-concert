package com.hhplusconcert.domain.payment.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Payment {
    private String paymentId;
    private String userId;
    private String reservationId;
    private int price;
    private Long createAt;

    public static Payment newInstance(
            String reservationId,
            String userId,
            int price
    ) {
        String newId = UUID.randomUUID().toString();
        return Payment.builder()
                .paymentId(newId)
                .reservationId(reservationId)
                .userId(userId)
                .price(price)
                .createAt(System.currentTimeMillis())
                .build();
    }
}
