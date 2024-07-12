package com.hhplusconcert.application.waitingToken.facade;

import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingTokenFlowFacade {
    //
    private final WaitingTokenService waitingTokenService;

    public String createWaitingToken(String userId, String seriesId) {
        //
        return this.waitingTokenService.create(userId, seriesId);
    }
}
