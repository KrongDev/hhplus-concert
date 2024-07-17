package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WaitingQueueFlowFacade {
    //
    private final WaitingQueueService waitingQueueService;
    private final WaitingTokenService waitingTokenService;

    @Value("${concert.limit.processCount:50}")
    private Long limitProcessCount;

    @Transactional
    public Long joinQueue(String tokenId) {
        //토큰 조회 없으면 에러
        WaitingToken token = this.waitingTokenService.loadWaitingToken(tokenId);
        token.validateExpired();
        long res;
        Long nowSequence = this.waitingQueueService.create(tokenId);
        try {
            WaitingQueue prev = this.waitingQueueService.loadPrevWaitingQueue(WaitingQueueStatus.READY);
            res = nowSequence - prev.getWaitingQueueId();
        } catch (NoSuchElementException e) {
            res = 1L;
        }
        return res;
    }

    @Transactional
    public void searchAndJoiningQueue() {
        //
        Long nowProcessCount = this.waitingQueueService.countWaitingQueueByStatus(WaitingQueueStatus.PROCESS);
        int addLimit = (int)(limitProcessCount - nowProcessCount);
        if (addLimit == 0) return;
        List<WaitingQueue> waitingQueues = this.waitingQueueService.loadWaitingQueueByStatusAndLimit(WaitingQueueStatus.READY, addLimit);
        waitingQueues.forEach(WaitingQueue::processToken);
        this.waitingQueueService.updateAll(waitingQueues);
    }

    @Transactional
    public void expiredQueue() {
        List<WaitingQueue> waitingQueues = this.waitingQueueService.loadExpiredQueue();
        waitingQueues.forEach(WaitingQueue::ended);
        this.waitingQueueService.updateAll(waitingQueues);

        List<String> tokenIds = waitingQueues.stream().map(WaitingQueue::getTokenId).toList();
        this.waitingTokenService.deleteWaitingTokens(tokenIds);
    }
}
