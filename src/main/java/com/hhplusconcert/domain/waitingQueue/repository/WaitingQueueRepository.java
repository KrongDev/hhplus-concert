package com.hhplusconcert.domain.waitingQueue.repository;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;

import java.util.List;
import java.util.Optional;

public interface WaitingQueueRepository {
    Long save(WaitingQueue waitingQueue);
    void saveAll(List<WaitingQueue> waitingQueues);
    WaitingQueue findByTokenId(String tokenId);
    Optional<WaitingQueue> findPrevQueue(WaitingQueueStatus status);
    List<WaitingQueue> findAllWithExpired();
    List<WaitingQueue> findAllByStatusAndOffsetLimit(WaitingQueueStatus status, int limit);
    Long countByStatus(WaitingQueueStatus status);
}
