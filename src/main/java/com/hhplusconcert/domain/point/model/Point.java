package com.hhplusconcert.domain.point.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    //
    private String userId;
    private int entityVersion;
    private int point;

    public void charge(int amount) {
        if(amount <= 0)
            throw new IllegalArgumentException("충전할 수 없는 금액입니다.");
        this.point += amount;
    }

    public void use(int amount) {
        if(amount <= 0)
            throw new IllegalArgumentException("사용할 수 없는 금액입니다.");
        if(amount > this.point)
            throw new RuntimeException("포인트가 부족합니다.");
        this.point -= amount;
    }

    public static Point newInstance(String userId, int point) {
        return Point.builder()
                .userId(userId)
                .point(point)
                .build();
    }
}
