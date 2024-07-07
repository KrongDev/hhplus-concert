package com.hhplusconcert.application.point.facade;

import com.hhplusconcert.domain.point.model.PointHistory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PointFlowFacadeTest {

    @Autowired
    private PointFlowFacade pointFlowFacade;
    @Autowired
    private PointSeekFacade pointSeekFacade;

    private final String userId = "test_userId";
    private final int amount = 1000;

    @Test
    @Order(1)
    @DisplayName("포인트 충전시 History 생성 테스트")
    void chargePoint() {
        //GIVEN
        this.pointFlowFacade.chargePoint(userId, amount);

        List<PointHistory> histories = this.pointSeekFacade.loadPointHistoryByPointId(userId);
        assertNotNull(histories);
        PointHistory history = histories.get(histories.size() - 1);
        assertEquals(userId, history.getUserId());
        assertEquals(amount, history.getAmount());
    }

    @Test
    @Order(2)
    @DisplayName("포인트 사용시 History 생성 테스트")
    void usePoint() {
        //GIVEN
        String paymentId = "test_paymentId";
        this.pointFlowFacade.usePoint(userId, amount, paymentId);

        List<PointHistory> histories = this.pointSeekFacade.loadPointHistoryByPointId(userId);
        assertNotNull(histories);
        PointHistory history = histories.get(histories.size() - 1);
        assertEquals(userId, history.getUserId());
        assertEquals(amount, history.getAmount());
    }
}
