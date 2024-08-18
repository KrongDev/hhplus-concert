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

    public void sendMessage(String topicId, Object message) {
        String payload = JsonUtil.toJson(message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicId, payload);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Producer: success >> message: {}, offset: {}", r.getProducerRecord().value(), r.getRecordMetadata().offset());
            } else {
                log.error("producer: failure >> message: {}", e.getMessage());
            }
        });
    }

    public void sendMessage(String topic, String payload) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, payload);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Producer: success >> message: {}, offset: {}", r.getProducerRecord().value(), r.getRecordMetadata().offset());
            } else {
                log.error("producer: failure >> message: {}", e.getMessage());
            }
        });
    }
}
