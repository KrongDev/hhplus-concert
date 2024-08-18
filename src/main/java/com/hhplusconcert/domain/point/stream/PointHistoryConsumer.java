package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointHistoryConsumer {
    //
    private final PointHistoryService pointHistoryService;

    @KafkaListener(topics = {"charged-point"}, groupId = "${concert.topic_groups.point}")
    public void ChargedPointConsume(String message) {
        ChargedPoint event = JsonUtil.fromJson(message, ChargedPoint.class);
        log.info("id {}, amount {}", event.requestUserId(), event.amount());

        String requestUserId = event.requestUserId();
        int amount = event.amount();
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.CHARGE);
    }

    @KafkaListener(topics = {"used-point"}, groupId = "point")
    public void UsedPointConsume(String message) {
        UsedPoint event = JsonUtil.fromJson(message, UsedPoint.class);
        log.info("id {}, amount {}", event.requestUserId(), event.amount());

        String requestUserId = event.requestUserId();
        String paymentId = event.paymentId();
        int amount = event.amount();
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.USE, paymentId);
    }
}
