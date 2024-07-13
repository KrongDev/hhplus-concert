package com.hhplusconcert.schedule;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WaitingQueueScheduler {
    //
    private final WaitingQueueService waitingQueueService;
    private final WaitingTokenService waitingTokenService;

    @Value("${concert.limit.processCount:50}")
    private Long limitProcessCount;

    /**
     * 현재 대기열 확인해서 인원 추가 입장 시키기
     */
    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void searchAndJoiningQueue () {
        //
        Long nowProcessCount = this.waitingQueueService.countWaitingQueueByStatus(WaitingQueueStatus.PROCESS);
        int addLimit = (int)(limitProcessCount - nowProcessCount);
        if (addLimit == 0) return;
        List<WaitingQueue> waitingQueues = this.waitingQueueService.loadWaitingQueueByStatusAndLimit(WaitingQueueStatus.READY, addLimit);
        waitingQueues.forEach(WaitingQueue::processToken);
        this.waitingQueueService.updateAll(waitingQueues);
    }

    /**
     * 대기열 만료시 종료후 토큰 삭제
     */
    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void expiredQueue () {
        //
        List<WaitingQueue> waitingQueues = this.waitingQueueService.loadExpiredQueue();
        waitingQueues.forEach(WaitingQueue::expired);
        this.waitingQueueService.updateAll(waitingQueues);

        List<String> tokenIds = waitingQueues.stream().map(WaitingQueue::getTokenId).toList();
        this.waitingTokenService.deleteWaitingTokens(tokenIds);
    }
}
