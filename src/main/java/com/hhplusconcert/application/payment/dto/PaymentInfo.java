package com.hhplusconcert.application.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
    private String paymentId;
    private String userId;
    private int amount;
    private PaymentEnum status;
    private LocalDateTime createAt;

    public enum PaymentEnum {
        COMPLETE
    }

    public static PaymentInfo sample() {
        return new PaymentInfo(
          "",
          "",
          10000,
          PaymentEnum.COMPLETE,
          LocalDateTime.now()
        );
    }
}
