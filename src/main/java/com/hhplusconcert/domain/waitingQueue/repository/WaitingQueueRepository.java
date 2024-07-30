package com.hhplusconcert.domain.waitingQueue.repository;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;

import java.util.List;

public interface WaitingQueueRepository {
    boolean save(WaitingQueue waitingQueue);
    Long findWaitingQueueCount(WaitingQueue.WaitingQueueKey key);
    List<WaitingQueue.WaitingQueueKey> findWaitingQueuesByJoinCount(Long joinCount);
    boolean  existWaitingQueue (WaitingQueue.WaitingQueueKey key);
    void deleteWaitingQueuesByRange(long endRange);
}
