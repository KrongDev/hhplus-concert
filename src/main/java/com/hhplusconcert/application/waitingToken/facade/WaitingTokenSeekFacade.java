package com.hhplusconcert.application.waitingToken.facade;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingTokenSeekFacade {
    //
    private final WaitingTokenService waitingTokenService;

    public WaitingToken loadWaitingToken(String tokenId) {
        //
        return waitingTokenService.loadWaitingToken(tokenId);
    }

    public WaitingToken loadWaitingTokenByUserIdAndSeriesId(String userId, String seriesId) {
        //
        return waitingTokenService.loadWaitingToken(userId, seriesId);
    }
}
