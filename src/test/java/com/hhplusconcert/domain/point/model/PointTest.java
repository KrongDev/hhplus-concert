package com.hhplusconcert.domain.point.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private final Point point = new Point();

    @Test
    @DisplayName("포인트 충전시 정상적이지 않은 포인트로 요청할 경우-INVALID_POINT 에러 발생")
    public void charge() {
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> point.charge(0));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("포인트 사용시 정상적이지 않은 포인트로 요청할 경우-INVALID_POINT 에러 발생")
    public void use() {
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> point.use(0));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("포인트 사용시 사용금액보다 보유 금액이 적을 경우-INSUFFICIENT_POINT 에러 발생")
    public void useButPointIsZero() {
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> point.use(10000));
        //THEN
        assertEquals(ErrorType.INSUFFICIENT_POINT, exception.getErrorType());
    }
}
