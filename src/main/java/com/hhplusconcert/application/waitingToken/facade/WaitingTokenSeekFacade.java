package com.hhplusconcert.application.waitingToken.facade;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingTokenSeekFacade {
    //
    private final WaitingTokenService waitingTokenService;

    public WaitingToken loadWaitingToken(String tokenId) {
        //
        return waitingTokenService.loadWaitingToken(tokenId);
    }
}
