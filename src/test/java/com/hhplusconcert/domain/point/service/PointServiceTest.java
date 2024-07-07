package com.hhplusconcert.domain.point.service;

import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;

    private final String userId = "test_userId";

    @BeforeEach
    public void setUp() {
        //GIVEN
        when(this.pointRepository.findById(userId)).thenReturn(Point.newInstance(userId, 0));
    }


    @Test
    @DisplayName("0원 이하 금액 충전시 에러 발생")
    void charge() {
        //WHEN
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pointService.charge(userId, -1);
        });
        // THEN
        String expectedMessage = "충전할 수 없는 금액입니다.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("포인트 사용시 사용할 수 없는 금액일 때 에러 발생")
    void useZeroPoint() {
        //WHEN
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pointService.use(userId, -1);
        });
        // THEN
        String expectedMessage = "사용할 수 없는 금액입니다.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("포인트 사용시 보유 금액보다 많을 경우 에러 발생")
    void useMorePoint() {
        //WHEN
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pointService.use(userId, 10000);
        });
        // THEN
        String expectedMessage = "포인트가 부족합니다.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
