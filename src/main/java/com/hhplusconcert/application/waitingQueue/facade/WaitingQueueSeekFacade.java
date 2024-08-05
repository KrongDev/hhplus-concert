package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueSeekFacade {
    //
    private final WaitingQueueService waitingQueueService;

    public Long loadNowWaitingCount(String userId, String seriesId) {
        //
        return this.waitingQueueService.loadWaitingQueueCount(userId, seriesId);
    }
}
