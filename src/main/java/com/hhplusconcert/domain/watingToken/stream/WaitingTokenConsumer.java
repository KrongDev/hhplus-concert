package com.hhplusconcert.domain.watingToken.stream;

import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingTokenConsumer {
    //
    private final WaitingTokenService waitingTokenService;

    @KafkaListener(topics = {"PaymentConfirmed"}, groupId = "${concert.topic_groups.waitingToken}")
    public void paymentConfirmedConsume(String message) {
        PaymentConfirmed event = JsonUtil.fromJson(message, PaymentConfirmed.class);

        String userId = event.getUserId();
        String seriesId = event.getSeriesId();
        this.waitingTokenService.deleteByUserIdAndSeriesId(userId, seriesId);
    }
}
