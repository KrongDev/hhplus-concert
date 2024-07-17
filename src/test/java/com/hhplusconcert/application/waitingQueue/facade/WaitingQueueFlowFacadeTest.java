package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class WaitingQueueFlowFacadeTest {
    //
    @Autowired
    private WaitingTokenService waitingTokenService;
    @Autowired
    private WaitingQueueFlowFacade waitingQueueFlowFacade;
    @Autowired
    private WaitingQueueService waitingQueueService;

    @Test
    @DisplayName("대기열 진입 N명 Limit 검증")
    public void joinQueue() throws InterruptedException {
        String userId = "test_userId";
        String seriesId = "test_seriesId";
        for(int i = 0; i < 55; i++) {
            String tokenId = this.waitingTokenService.create(userId + i, seriesId + i);
            Long queueCount = waitingQueueFlowFacade.joinQueue(tokenId);
        }
        Thread.sleep(3000);
        Long count = this.waitingQueueService.countWaitingQueueByStatus(WaitingQueueStatus.PROCESS);
        assertEquals(50, count);
    }
}
