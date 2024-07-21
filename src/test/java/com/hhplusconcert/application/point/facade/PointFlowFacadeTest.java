package com.hhplusconcert.application.point.facade;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.point.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PointFlowFacadeTest {
    @Autowired
    private PointFlowFacade pointFlowFacade;
    @Autowired
    private PointService pointService;

    private final String userId = "test_userId";

    @BeforeEach
    public void setUp() {
        //
        pointService.create(userId);
    }

    @Test
    @DisplayName("포인트 충전시 충전금액이 부적절한 경우")
    void chargePoint() {
        //GIVEN
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointFlowFacade.chargePoint(userId, -1000));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("포인트 사용시 사용금액이 부적절한 경우")
    void usePointInvalidPoint() {
        //GIVEN
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointFlowFacade.usePoint(userId, -1000, ""));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("포인트 사용시 잔여 포인트가 부족한 경우")
    void usePointInsufficientPoint() {
        //GIVEN
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointFlowFacade.usePoint(userId, 1000, ""));
        //THEN
        assertEquals(ErrorType.INSUFFICIENT_POINT, exception.getErrorType());
    }
}
