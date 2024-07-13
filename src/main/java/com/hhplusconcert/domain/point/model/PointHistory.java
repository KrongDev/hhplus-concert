package com.hhplusconcert.domain.point.model;

import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory {
    //
    private String pointHistoryId;
    private String paymentId;
    private String userId;
    private int amount;
    private PointHistoryStatus status;
    private long createAt;

    public static PointHistory newInstance(
            String userId,
            int amount,
            PointHistoryStatus status,
            String paymentId
    ) {
        String newId = UUID.randomUUID().toString();
        return PointHistory.builder()
                .pointHistoryId(newId)
                .paymentId(paymentId)
                .userId(userId)
                .amount(amount)
                .status(status)
                .createAt(System.currentTimeMillis())
                .build();
    }
}
