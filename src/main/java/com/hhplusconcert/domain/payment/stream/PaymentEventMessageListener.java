package com.hhplusconcert.domain.payment.stream;

import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventMessageListener {
    //
    private final KafkaProducerCluster kafkaProducerCluster;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMessageHandler(PaymentConfirmed event) {
        //
        this.kafkaProducerCluster.sendMessage(event);
    }
}
