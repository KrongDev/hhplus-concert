package com.hhplusconcert.domain.payment.stream;

import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.outbox.service.OutboxService;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentOutboxHandler {
    //
    private final OutboxService outboxService;

    @KafkaListener(topics = {"PaymentConfirmed"}, groupId = "${concert.topic_groups.outbox}")
    public void paymentConfirmed(String message) {
        PaymentConfirmed event = JsonUtil.fromJson(message, PaymentConfirmed.class);
        this.outboxService.successPublish(event.getEventId());
    }
}
