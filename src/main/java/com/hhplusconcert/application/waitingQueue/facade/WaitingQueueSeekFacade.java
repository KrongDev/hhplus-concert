package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.common.exception.model.CustomGlobalException;
import com.hhplusconcert.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WaitingQueueSeekFacade {
    //
    private final WaitingQueueService waitingQueueService;

    public Long loadNowWaitingCount(String tokenId) {
        //
        WaitingQueue thisQueue = this.waitingQueueService.loadWaitingQueue(tokenId);
        if(thisQueue.isEnded())
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_EXPIRED);
        if(thisQueue.isProcess())
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_PROCESSING);
        WaitingQueue prevQueue = this.waitingQueueService.loadPrevWaitingQueue(WaitingQueueStatus.READY);
        if(Objects.isNull(prevQueue)) return 1L;
        return thisQueue.getWaitingQueueId() - prevQueue.getWaitingQueueId();
    }
}
