package com.hhplusconcert.interfaces.schedule;

import com.hhplusconcert.application.waitingQueue.facade.WaitingQueueFlowFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WaitingQueueScheduler {
    //
    private final WaitingQueueFlowFacade waitingQueueFlowFacade;

    /**
     * 현재 대기열 확인해서 인원 추가 입장 시키기
     */
    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void searchAndJoiningQueue () {
        //
        this.waitingQueueFlowFacade.searchAndJoiningQueue();
    }

    /**
     * 대기열 만료시 종료후 토큰 삭제
     */
    @Scheduled(fixedDelay = 3000)
    public void expiredQueue () {
        //
        this.waitingQueueFlowFacade.expiredQueue();
    }
}
