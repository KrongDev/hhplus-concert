package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointHistoryConsumer {
    //
    private final PointHistoryService pointHistoryService;

    @KafkaListener(topics = {"ChargedPoint"}, groupId = "${concert.topic_groups.point}")
    public void chargedPointConsume(String message) {
        ChargedPoint event = JsonUtil.fromJson(message, ChargedPoint.class);
        log.info("id {}, amount {}", event.getRequestUserId(), event.getAmount());

        String requestUserId = event.getRequestUserId();
        int amount = event.getAmount();
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.CHARGE);
    }

    @KafkaListener(topics = {"UsedPoint"}, groupId = "${concert.topic_groups.point}")
    public void usedPointConsume(String message) {
        UsedPoint event = JsonUtil.fromJson(message, UsedPoint.class);
        log.info("id {}, amount {}", event.getRequestUserId(), event.getAmount());

        String requestUserId = event.getRequestUserId();
        String paymentId = event.getPaymentId();
        int amount = event.getAmount();
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.USE, paymentId);
    }
}
