package com.hhplusconcert.domain.point.stream;


import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.outbox.service.OutboxService;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointOutboxHandler {
    //
    private final OutboxService outboxService;

    @KafkaListener(topics = {"ChargedPoint"}, groupId = "${concert.topic_groups.outbox}")
    public void chargedPoint(String message) {
        ChargedPoint event = JsonUtil.fromJson(message, ChargedPoint.class);
        this.outboxService.successPublish(event.getEventId());
    }

    @KafkaListener(topics = {"UsedPoint"}, groupId = "${concert.topic_groups.outbox}")
    public void usedPoint(String message) {
        UsedPoint event = JsonUtil.fromJson(message, UsedPoint.class);
        this.outboxService.successPublish(event.getEventId());
    }
}
