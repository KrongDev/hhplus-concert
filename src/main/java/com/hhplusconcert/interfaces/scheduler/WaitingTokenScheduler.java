package com.hhplusconcert.interfaces.scheduler;

import com.hhplusconcert.application.waitingToken.facade.WaitingTokenFlowFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WaitingTokenScheduler {
    //
    private final WaitingTokenFlowFacade waitingQueueFlowFacade;

    /**
     * 토큰 만료시 토큰 삭제 && 대기열에서 만료 처리
     */
    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void searchAndJoiningQueue () {
        //
        this.waitingQueueFlowFacade.processExpiredTokens();
    }

}
