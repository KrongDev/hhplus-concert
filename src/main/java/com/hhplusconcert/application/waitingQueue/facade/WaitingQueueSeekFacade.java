package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
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
        WaitingQueue thisQueue = this.waitingQueueService.loadWaitingQueue(tokenId);
        WaitingQueue prevQueue = this.waitingQueueService.loadPrevWaitingQueue(WaitingQueueStatus.READY);
        return thisQueue.getWaitingQueueId() - prevQueue.getWaitingQueueId();
    }
}
