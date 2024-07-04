package com.hhplusconcert.application.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryInfo {
    private String pointHistoryId;
    private String paymentId;
    private String pointId;
    private String userId;
    private int amount;
    private PointHistoryEnum status;
    private LocalDateTime createAt;

    public enum PointHistoryEnum {
        CHARGE,
        USE
    }

    public static PointHistoryInfo sample() {
        return new PointHistoryInfo(
                "",
                "",
                "",
                "",
                10000,
                PointHistoryEnum.CHARGE,
                LocalDateTime.now()
        );
    }
}
