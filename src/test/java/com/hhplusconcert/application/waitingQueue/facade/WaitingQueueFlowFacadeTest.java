package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.infra.waitingToken.orm.WaitingTokenJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class WaitingQueueFlowFacadeTest {
    private static final Logger log = LoggerFactory.getLogger(WaitingQueueFlowFacadeTest.class);
    //
    @Autowired
    private WaitingQueueFlowFacade waitingQueueFlowFacade;
    @Autowired
    private WaitingTokenJpaRepository waitingTokenJpaRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TruncateTableComponent truncateTableComponent;
    private final String queueKey = "concert-waiting-queue-test";

    @BeforeEach
    public void setUp() {
        // Start a new transaction
        truncateTableComponent.truncateTable(() -> redisTemplate.delete(queueKey), "waiting_token");
    }

    @Test
    @DisplayName("대기열 진입 N명 Limit 검증")
    public void joinQueue() throws InterruptedException {
        String userId = "test_userId";
        String seriesId = "test_seriesId";
        for(int i = 0; i < 100; i++) {
            try {
                waitingQueueFlowFacade.joinQueue(userId + i, seriesId);
            }catch (CustomGlobalException e) {
                log.warn(e.getMessage());
            }
        }
        Thread.sleep(3000);
        Long count = this.waitingTokenJpaRepository.count();

        assertEquals(50, count);
    }
}
