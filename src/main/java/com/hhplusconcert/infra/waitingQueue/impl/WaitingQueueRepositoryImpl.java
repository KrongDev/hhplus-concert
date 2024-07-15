package com.hhplusconcert.infra.waitingQueue.impl;

import com.hhplusconcert.domain.common.exception.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.ErrorType;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import com.hhplusconcert.domain.waitingQueue.repository.WaitingQueueRepository;
import com.hhplusconcert.infra.waitingQueue.orm.WaitingQueueJpoRepository;
import com.hhplusconcert.infra.waitingQueue.orm.jpo.WaitingQueueJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {
    //
    private final WaitingQueueJpoRepository waitingQueueJpoRepository;

    @Override
    public Long save(WaitingQueue waitingQueue) {
        //
        WaitingQueueJpo jpo = this.waitingQueueJpoRepository.save(new WaitingQueueJpo(waitingQueue));
        return jpo.getWaitingQueueId();
    }

    @Override
    public void saveAll(List<WaitingQueue> waitingQueues) {
        //
        this.waitingQueueJpoRepository.saveAll(waitingQueues.stream().map(WaitingQueueJpo::new).toList());
    }

    @Override
    public WaitingQueue findByTokenIdWithThrow(String tokenId) {
        //
        Optional<WaitingQueueJpo> jpo = this.waitingQueueJpoRepository.findByTokenId(tokenId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_NOT_FOUND);
        return jpo.get().toDomain();
    }

    // 이전 대기열 토큰 조회
    @Override
    public Optional<WaitingQueue> findPrevQueue(WaitingQueueStatus status) {
        //
        Optional<WaitingQueueJpo> jpo = this.waitingQueueJpoRepository.findFirstByStatusOrderByCreateAt(status);
        return jpo.map(WaitingQueueJpo::toDomain);
    }

    @Override
    public List<WaitingQueue> findAllWithExpired() {
        List<WaitingQueueJpo> jpos = this.waitingQueueJpoRepository.findAllByExpiredAtGreaterThanEqual(System.currentTimeMillis());
        return jpos.stream().map(WaitingQueueJpo::toDomain).toList();
    }

    @Override
    public List<WaitingQueue> findAllByStatusAndOffsetLimit(WaitingQueueStatus status, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(ASC, "createAt"));
        List<WaitingQueueJpo> jpos = this.waitingQueueJpoRepository.findAllByStatus(status, pageable);
        return jpos.stream().map(WaitingQueueJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public Long countByStatus(WaitingQueueStatus status) {
        //
        return this.waitingQueueJpoRepository.countByStatus(status);
    }
}
