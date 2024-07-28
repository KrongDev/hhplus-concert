package com.hhplusconcert.interfaces.scheduler;

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
    public void activateWaitingQueueItems () {
        //
        this.waitingQueueFlowFacade.activateWaitingQueueItems();
    }
}
