package com.hhplusconcert.interfaces.consumer.payment;

import com.hhplusconcert.application.outbox.OutboxFlowFacade;
import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentOutboxHandler {
    //
    private final OutboxFlowFacade outboxFlowFacade;

    @KafkaListener(topics = {PaymentConfirmed.topicId}, groupId = "${concert.topic_groups.outbox}")
    public void paymentConfirmed(String message) {
        PaymentConfirmed event = JsonUtil.fromJson(message, PaymentConfirmed.class);
        this.outboxFlowFacade.successPublish(event.getEventId());
    }
}
