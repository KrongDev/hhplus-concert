package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.outbox.service.OutboxService;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointEventMessageListener {
    private final OutboxService outboxService;
    private final KafkaProducerCluster kafkaProducerCluster;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMessageHandler(ChargedPoint event) {
        //
        try {
            this.kafkaProducerCluster.sendMessage(event);
            this.outboxService.published(event.getEventId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMessageHandler(UsedPoint event) {
        //
        try {
            this.kafkaProducerCluster.sendMessage(event);
            this.outboxService.published(event.getEventId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
