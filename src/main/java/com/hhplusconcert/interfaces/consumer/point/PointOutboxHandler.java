package com.hhplusconcert.interfaces.consumer.point;


import com.hhplusconcert.application.outbox.OutboxFlowFacade;
import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointOutboxHandler {
    //
    private final OutboxFlowFacade outboxFlowFacade;

    @KafkaListener(topics = {ChargedPoint.topicId}, groupId = "${concert.topic_groups.outbox}")
    public void chargedPoint(String message) {
        ChargedPoint event = JsonUtil.fromJson(message, ChargedPoint.class);
        this.outboxFlowFacade.successPublish(event.getEventId());
    }

    @KafkaListener(topics = {UsedPoint.topicId}, groupId = "${concert.topic_groups.outbox}")
    public void usedPoint(String message) {
        UsedPoint event = JsonUtil.fromJson(message, UsedPoint.class);
        this.outboxFlowFacade.successPublish(event.getEventId());
    }
}
