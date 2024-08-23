package com.hhplusconcert.domain.waitingQueue.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    //
    private final WaitingQueueRepository waitingQueueRepository;

    @Transactional
    public void create( String userId, String seriesId ) {
        if(this.existWaitingQueue(userId, seriesId))
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_ALREADY_EXIST);
        WaitingQueue waitingQueue = WaitingQueue.newInstance(userId, seriesId);
        this.waitingQueueRepository.save(waitingQueue);
    }

    public Long loadWaitingQueueCount(String userId, String seriesId) {
        //
        return this.waitingQueueRepository.findWaitingQueueCount(new WaitingQueue.WaitingQueueKey(userId, seriesId));
    }

    public List<WaitingQueue.WaitingQueueKey> loadActiveWaitingQueues(Long jointCount) {
        //
        return this.waitingQueueRepository.findWaitingQueuesByJoinCount(jointCount);
    }

    public boolean existWaitingQueue(String userId, String seriesId) {
        //
        return this.waitingQueueRepository.existWaitingQueue(new WaitingQueue.WaitingQueueKey(userId, seriesId));
    }

    public void deleteWaitingQueuesByRange(long endRange) {
        //
        this.waitingQueueRepository.deleteWaitingQueuesByRange(endRange);
    }
}
