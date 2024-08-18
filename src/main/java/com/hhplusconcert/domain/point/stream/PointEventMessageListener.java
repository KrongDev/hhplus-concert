package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointEventMessageListener {
    private final KafkaProducerCluster kafkaProducerCluster;

    @TransactionalEventListener
    public void sendMessageHandler(ChargedPoint event) {
        //
        this.kafkaProducerCluster.sendMessage(ChargedPoint.topicId, event);
    }

    @TransactionalEventListener
    public void sendMessageHandler(UsedPoint event) {
        //
        this.kafkaProducerCluster.sendMessage(UsedPoint.topicId, event);
    }
}
