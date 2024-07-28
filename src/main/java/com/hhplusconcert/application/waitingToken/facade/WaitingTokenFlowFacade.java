package com.hhplusconcert.application.waitingToken.facade;

import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WaitingTokenFlowFacade {
    //
    private final WaitingTokenService waitingTokenService;

    @Transactional
    public void processExpiredTokens() {
        //
        this.waitingTokenService.processExpiredTokens();
    }

    @Transactional
    public void healthCheck(String tokenId) {
        //
        this.waitingTokenService.healthCheck(tokenId);
    }
}
