package com.hhplusconcert.application.waitingToken.facade;

import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitingTokenFlowFacade {
    //
    private final WaitingTokenService waitingTokenService;
    private final WaitingQueueService waitingQueueService;

    public String createWaitingToken(String userId, String seriesId) {
        //
        return this.waitingTokenService.create(userId, seriesId);
    }

    @Transactional
    public void processExpiredTokens() {
        List<String> tokenIds = this.waitingTokenService.processExpiredTokens();
        this.waitingQueueService.queuesExpiredByTokens(tokenIds);
    }

    @Transactional
    public void healthCheck(String tokenId) {
        this.waitingTokenService.healthCheck(tokenId);
    }
}
