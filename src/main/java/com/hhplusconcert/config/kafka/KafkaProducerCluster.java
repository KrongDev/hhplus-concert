package com.hhplusconcert.config.kafka;

import com.hhplusconcert.common.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerCluster {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Object message) {
        String payload = JsonUtil.toJson(message);
        String simpleClassName = message.getClass().getSimpleName();
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(simpleClassName, payload);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Producer: success >> message: {}, offset: {}", r.getProducerRecord().value(), r.getRecordMetadata().offset());
            } else {
                log.error("producer: failure >> message: {}", e.getMessage());
            }
        });
    }
}
