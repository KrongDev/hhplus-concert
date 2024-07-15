package com.hhplusconcert.domain.waitingQueue.service;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
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
    public Long create( String tokenId ) {
        WaitingQueue waitingQueue = WaitingQueue.newInstance(tokenId);

        return this.waitingQueueRepository.save(waitingQueue);
    }

    public WaitingQueue loadWaitingQueue(String tokenId) {
        //
        return this.waitingQueueRepository.findByTokenIdWithThrow(tokenId);
    }

    public WaitingQueue loadPrevWaitingQueue(WaitingQueueStatus status) {
        //
        return this.waitingQueueRepository.findPrevQueue(status).orElse(null);
    }

    public List<WaitingQueue> loadExpiredQueue() {
        //
        return this.waitingQueueRepository.findAllWithExpired();
    }

    public List<WaitingQueue> loadWaitingQueueByStatusAndLimit(WaitingQueueStatus status, int offsetLimit) {
        //
        return this.waitingQueueRepository.findAllByStatusAndOffsetLimit(status, offsetLimit);
    }

    public Long countWaitingQueueByStatus(WaitingQueueStatus status) {
        //
        return this.waitingQueueRepository.countByStatus(status);
    }

    @Transactional
    public void update(WaitingQueue waitingQueue) {
        //
        this.waitingQueueRepository.save(waitingQueue);
    }

    @Transactional
    public void updateAll(List<WaitingQueue> waitingQueues) {
        //
        this.waitingQueueRepository.saveAll(waitingQueues);
    }
}
