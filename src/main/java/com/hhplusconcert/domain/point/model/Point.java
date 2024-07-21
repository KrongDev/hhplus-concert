package com.hhplusconcert.domain.point.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
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
            throw new CustomGlobalException(ErrorType.INVALID_POINT);
        this.point += amount;
    }

    public void use(int amount) {
        if(amount <= 0)
            throw new CustomGlobalException(ErrorType.INVALID_POINT);
        if(amount > this.point)
            throw new CustomGlobalException(ErrorType.INSUFFICIENT_POINT);
        this.point -= amount;
    }

    public static Point newInstance(String userId, int point) {
        return Point.builder()
                .userId(userId)
                .point(point)
                .build();
    }
}
