package com.hhplusconcert.application.waitingToken.facade;

import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
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
    public void expiredToken() {
        List<WaitingToken> tokens = this.waitingTokenService.loadWaitingTokensByExpired();
        List<String> tokenIds = tokens.stream().map(WaitingToken::getTokenId).toList();
        this.waitingTokenService.deleteWaitingTokens(tokenIds);
        this.waitingQueueService.queuesExpiredByTokens(tokenIds);
    }

    @Transactional
    public void healthCheck(String tokenId) {
        this.waitingTokenService.healthCheck(tokenId);
    }
}
