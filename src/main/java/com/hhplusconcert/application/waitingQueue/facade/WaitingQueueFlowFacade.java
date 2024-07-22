package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class WaitingQueueFlowFacade {
    //
    private final WaitingQueueService waitingQueueService;
    private final WaitingTokenService waitingTokenService;


    public Long joinQueue(String tokenId) {
        //토큰 조회 없으면 에러
        WaitingToken token = this.waitingTokenService.loadNotExpiredWaitingToken(tokenId);
        long res;
        Long nowSequence = this.waitingQueueService.create(token.getTokenId());
        try {
            WaitingQueue prev = this.waitingQueueService.loadPrevWaitingQueue(WaitingQueueStatus.READY);
            res = nowSequence - prev.getWaitingQueueId();
        } catch (NoSuchElementException e) {
            res = 1L;
        }
        return res;
    }

    public void activateWaitingQueueItems() {
        //
        this.waitingQueueService.activateWaitingQueueItems();
    }

    @Transactional
    public void expiredQueue() {
        List<String> tokenIds = this.waitingQueueService.expireStaleWaitingQueueItems();
        this.waitingTokenService.deleteWaitingTokens(tokenIds);
    }
}
