package com.hhplusconcert.domain.outbox.scheduler;

import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {
    //
    private final OutboxService outboxService;
    private final KafkaProducerCluster kafkaProducerCluster;

    @Scheduled(fixedDelay = 1000)
    public void republishFailedMessages() {
        //
        List<Outbox> republishMessages = this.outboxService.loadUnpublishedAndOlderThanOneMinute();
        republishMessages.parallelStream().forEach(outbox -> {
            try {
                this.kafkaProducerCluster.sendMessage(outbox.getTopic(), outbox.getPayload());
                this.outboxService.successPublish(outbox.getId());
            }catch (Exception e) {
                this.outboxService.failPublish(outbox.getId());
            }
        });
    }
}
