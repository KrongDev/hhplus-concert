package com.hhplusconcert.application.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



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
    private Long createAt;

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
                System.currentTimeMillis()
        );
    }
}
