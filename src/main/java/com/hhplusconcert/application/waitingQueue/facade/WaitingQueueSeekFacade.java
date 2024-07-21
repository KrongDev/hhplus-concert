package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingQueueSeekFacade {
    //
    private final WaitingQueueService waitingQueueService;

    public Long loadNowWaitingCount(String tokenId) {
        //
        return this.waitingQueueService.loadNowWaitingCount(tokenId);
    }

    public WaitingQueue loadNowWaitingQueue(String tokenId) {
        //
        return this.waitingQueueService.loadWaitingQueueByTokenId(tokenId);
    }
}
