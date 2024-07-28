package com.hhplusconcert.config.kafka;

import com.hhplusconcert.common.kafka.model.KafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerCluster {

    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    public void sendMessage(KafkaEvent payload) {
        CompletableFuture<SendResult<String, KafkaEvent>> future = kafkaTemplate.send(topicName, payload);
        future.whenComplete((r, e) -> {
            if (e == null) {
                log.info("Producer: success >> message: {}, offset: {}", r.getProducerRecord().value(), r.getRecordMetadata().offset());
            } else {
                log.error("producer: failure >> message: {}", e.getMessage());
            }
        });
    }
}
