//package com.hhplusconcert.infra.waitingQueue.impl;
//
//import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
//import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
//import com.hhplusconcert.domain.waitingQueue.repository.WaitingQueueRepository;
//import com.hhplusconcert.infra.waitingQueue.orm.WaitingQueueJpaRepository;
//import com.hhplusconcert.infra.waitingQueue.orm.jpo.WaitingQueueJpo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.springframework.data.domain.Sort.Direction.ASC;
//
//@Repository
//@RequiredArgsConstructor
//public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {
//    //
//    private final WaitingQueueJpaRepository waitingQueueJpaRepository;
//
//    @Override
//    public Long save(WaitingQueue waitingQueue) {
//        //
//        WaitingQueueJpo jpo = this.waitingQueueJpaRepository.save(new WaitingQueueJpo(waitingQueue));
//        return jpo.getWaitingQueueId();
//    }
//
//    @Override
//    public void saveAll(List<WaitingQueue> waitingQueues) {
//        //
//        this.waitingQueueJpaRepository.saveAll(waitingQueues.stream().map(WaitingQueueJpo::new).toList());
//    }
//
//    @Override
//    public WaitingQueue findByTokenId(String tokenId) {
//        //
//        Optional<WaitingQueueJpo> jpo = this.waitingQueueJpaRepository.findByTokenId(tokenId);
//        return jpo.map(WaitingQueueJpo::toDomain).orElse(null);
//    }
//
//    // 이전 대기열 토큰 조회
//    @Override
//    public WaitingQueue findPrevQueue(WaitingQueueStatus status) {
//        //
//        Optional<WaitingQueueJpo> jpo = this.waitingQueueJpaRepository.findFirstByStatusOrderByCreateAt(status);
//        return jpo.map(WaitingQueueJpo::toDomain).orElse(null);
//    }
//
//    @Override
//    public List<WaitingQueue> findAllWithExpired(long expiredAt) {
//        List<WaitingQueueJpo> jpos = this.waitingQueueJpaRepository.findAllByStatusIsNotAndExpiredAtLessThanEqual(WaitingQueueStatus.END,expiredAt);
//        return jpos.stream().map(WaitingQueueJpo::toDomain).toList();
//    }
//
//    @Override
//    public List<WaitingQueue> findAllByStatusAndOffsetLimit(WaitingQueueStatus status, int size) {
//        Pageable pageable = PageRequest.of(0, size, Sort.by(ASC, "createAt"));
//        List<WaitingQueueJpo> jpos = this.waitingQueueJpaRepository.findAllByStatus(status, pageable);
//        return jpos.stream().map(WaitingQueueJpo::toDomain).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<WaitingQueue> findAllByTokenIds(List<String> tokenIds) {
//        List<WaitingQueueJpo> jpos = this.waitingQueueJpaRepository.findAllByTokenIdIn(tokenIds);
//        return jpos.stream().map(WaitingQueueJpo::toDomain).collect(Collectors.toList());
//    }
//
//    @Override
//    public Long countByStatus(WaitingQueueStatus status) {
//        //
//        return this.waitingQueueJpaRepository.countByStatus(status);
//    }
//}
