//package com.hhplusconcert.infra.waitingQueue.orm;
//
//import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
//import com.hhplusconcert.infra.waitingQueue.orm.jpo.WaitingQueueJpo;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueueJpo, Long> {
//    Optional<WaitingQueueJpo> findByTokenId(String tokenId);
//    Optional<WaitingQueueJpo> findFirstByStatusOrderByCreateAt(WaitingQueueStatus status);
//    List<WaitingQueueJpo> findAllByStatus(WaitingQueueStatus status, Pageable pageable);
//    List<WaitingQueueJpo> findAllByStatusIsNotAndExpiredAtLessThanEqual(WaitingQueueStatus status, Long expiredAt);
//    List<WaitingQueueJpo> findAllByTokenIdIn(List<String> tokenIds);
//    Long countByStatus(WaitingQueueStatus status);
//}
