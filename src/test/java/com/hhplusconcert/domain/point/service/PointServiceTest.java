package com.hhplusconcert.domain.point.service;

import com.hhplusconcert.domain.common.exception.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.ErrorType;
import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.repository.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    @Test
    @DisplayName("포인트 충전시 사용불가 금액일경우")
    public void invalidPointCharge() {
        //GIVEN
        String userId = "test_userId";
        when(pointRepository.findByIdWithThrow(userId)).thenReturn(Point.builder().userId(userId).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointService.charge(userId, 0));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("포인트 사용시 사용불가 금액일경우")
    public void invalidPointUse() {
        //GIVEN
        String userId = "test_userId";
        when(pointRepository.findByIdWithThrow(userId)).thenReturn(Point.builder().userId(userId).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointService.use(userId, 0));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("포인트 사용시 사용금액이 잔여금액보다 많을 경우")
    public void insufficientPointUse() {
        //GIVEN
        String userId = "test_userId";
        when(pointRepository.findByIdWithThrow(userId)).thenReturn(Point.builder().userId(userId).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointService.use(userId, 100));
        //THEN
        assertEquals(ErrorType.INSUFFICIENT_POINT, exception.getErrorType());
    }
}
