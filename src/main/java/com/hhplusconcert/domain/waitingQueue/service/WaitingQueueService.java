package com.hhplusconcert.domain.waitingQueue.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {
    //
    private final WaitingQueueRepository waitingQueueRepository;

    @Value("${concert.limit.processCount:50}")
    private Long limitProcessCount;

    @Transactional
    public Long create( String tokenId ) {
        WaitingQueue waitingQueue = WaitingQueue.newInstance(tokenId);

        return this.waitingQueueRepository.save(waitingQueue);
    }

    public WaitingQueue loadWaitingQueueByTokenId(String tokenId) {
        //
        WaitingQueue queue = this.waitingQueueRepository.findByTokenId(tokenId);
        if(Objects.isNull(queue))
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_NOT_FOUND);
        return queue;
    }

    public Long loadNowWaitingCount(String tokenId) {
        // 현재 대기 조회
        WaitingQueue queue = this.loadWaitingQueueByTokenId(tokenId);
        queue.verifyQueueStatusReady();
        //이전 대기 조회
        WaitingQueue prevQueue = this.loadPrevWaitingQueue(WaitingQueueStatus.READY);

        if(Objects.isNull(prevQueue)) return 1L;
        return queue.getWaitingQueueId() - prevQueue.getWaitingQueueId();
    }

    public WaitingQueue loadPrevWaitingQueue(WaitingQueueStatus status) {
        //
        return this.waitingQueueRepository.findPrevQueue(status);
    }

    public List<WaitingQueue> loadExpiredQueue() {
        //
        return this.waitingQueueRepository.findAllWithExpired(System.currentTimeMillis());
    }

    public Long countWaitingQueueByStatus(WaitingQueueStatus status) {
        //
        return this.waitingQueueRepository.countByStatus(status);
    }

    @Transactional
    public void queuesExpiredByToken(String tokenId) {
        WaitingQueue queue = this.loadWaitingQueueByTokenId(tokenId);
        queue.ended();
        this.update(queue);
    }

    @Transactional
    public void queuesExpiredByTokens(List<String> tokenIds) {
        List<WaitingQueue> queues = this.waitingQueueRepository.findAllByTokenIds(tokenIds);
        queues.forEach(WaitingQueue::ended);
        this.updateAll(queues);
    }

    @Transactional
    public void activateWaitingQueueItems() {
        Long nowProcessCount = this.countWaitingQueueByStatus(WaitingQueueStatus.PROCESS);
        int addLimit = (int)(limitProcessCount - nowProcessCount);
        if (addLimit == 0) return;
        List<WaitingQueue> waitingQueues = this.waitingQueueRepository.findAllByStatusAndOffsetLimit(WaitingQueueStatus.READY, addLimit);
        waitingQueues.forEach(WaitingQueue::processToken);
        this.updateAll(waitingQueues);
    }

    @Transactional
    public List<String> expireStaleWaitingQueueItems() {
        List<WaitingQueue> waitingQueues = this.loadExpiredQueue();
        waitingQueues.forEach(WaitingQueue::ended);
        this.updateAll(waitingQueues);
        return waitingQueues.stream().map(WaitingQueue::getTokenId).toList();
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
