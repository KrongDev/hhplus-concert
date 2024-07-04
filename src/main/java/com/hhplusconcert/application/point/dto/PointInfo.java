package com.hhplusconcert.application.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointInfo {
    private String pointId;
    private String userId;
    private int point;

    public static PointInfo sample() {
        return new PointInfo(
            "",
            "",
            10000
        );
    }
}
