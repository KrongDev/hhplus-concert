package com.hhplusconcert.application.waitingQueue.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WaitingQueueFlowFacadeTest {
    //
    @Autowired
    private WaitingQueueFlowFacade waitingQueueFlowFacade;

    @Test
    @DisplayName("대기열 진입 N명 Limit 검증")
    public void joinQueue() throws InterruptedException {
        String userId = "test_userId";
        String seriesId = "test_seriesId";
        for(int i = 0; i < 55; i++) {
            Long queueCount = waitingQueueFlowFacade.joinQueue(userId, seriesId);
        }
        Thread.sleep(3000);
//        Long count = this.waitingQueueService.countWaitingQueueByStatus(WaitingQueueStatus.PROCESS);
//        assertEquals(50, count);
    }
}
