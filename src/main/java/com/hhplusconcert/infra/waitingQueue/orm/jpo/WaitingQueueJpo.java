//package com.hhplusconcert.infra.waitingQueue.orm.jpo;
//
//import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
//import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
//import com.hhplusconcert.infra.common.JpoEntity;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.beans.BeanUtils;
//
//import java.io.Serializable;
//import java.util.Calendar;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name="waiting_queue")
//public class WaitingQueueJpo implements JpoEntity<WaitingQueue> {
//    //
//    @EmbeddedId
//    private WaitingQueueKey key;
//    private Long createAt;
////    private Long updateAt;
//    // 입장시부터 종료 시까지 작업할 수 있는 시간
////    private Long expiredAt;
////    @Enumerated(EnumType.STRING)
////    public WaitingQueueStatus status;
//
//    public WaitingQueueJpo(WaitingQueue waitingQueue) {
//        //
//        BeanUtils.copyProperties(waitingQueue, this);
//        this.key = new WaitingQueueKey(waitingQueue.getUserId(), waitingQueue.getSeriesId());
//    }
//
//
//
//
//    @Override
//    public WaitingQueue toDomain() {
//    return WaitingQueue.builder()
//            .userId(this.key.getUserId())
//            .seriesId(this.key.getSeriesId())
//            .createAt(createAt)
//            .build();
//    }
//}
