package com.hhplusconcert.interfaces.cluster;

import com.hhplusconcert.common.kafka.model.KafkaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerCluster {
    //
    @KafkaListener(topics = {"${spring.kafka.template.default-topic}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consume(KafkaEvent message) {
        log.info("id {}, message {}", message.getId(), message.getMessage());
    }
}
