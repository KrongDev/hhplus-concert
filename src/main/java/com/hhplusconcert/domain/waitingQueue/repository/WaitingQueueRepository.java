package com.hhplusconcert.domain.waitingQueue.repository;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;

import java.util.List;
import java.util.Optional;

public interface WaitingQueueRepository {
    Long save(WaitingQueue waitingQueue);
    void saveAll(List<WaitingQueue> waitingQueues);
    WaitingQueue findByTokenIdWithThrow(String tokenId);
    Optional<WaitingQueue> findPrevQueue(WaitingQueueStatus status);
    List<WaitingQueue> findAllWithExpired(long expiredAt);
    List<WaitingQueue> findAllByStatusAndOffsetLimit(WaitingQueueStatus status, int limit);
    List<WaitingQueue> findAllByTokenIds(List<String> tokenIds);
    Long countByStatus(WaitingQueueStatus status);
}
