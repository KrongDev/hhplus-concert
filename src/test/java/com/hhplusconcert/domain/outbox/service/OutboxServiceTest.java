package com.hhplusconcert.domain.outbox.service;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.common.Event;
import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class OutboxServiceTest {

    @Autowired
    private TruncateTableComponent truncateTableComponent;
    @Autowired
    private OutboxService outboxService;
    @Autowired
    private KafkaProducerCluster producerCluster;

    private final String topicId = ChargedPoint.topicId;
    private final Event event = ChargedPoint.of("test_userId", 10000);

    @BeforeEach
    public void setUp() {
        //
        truncateTableComponent.truncateTable(() -> {}, "outbox");
    }

    @Test
    @DisplayName("정상 발행 실패하였을 때 count++")
    void failPublish() {
        //GIVEN
        outboxService.create(topicId, event);
        //WHEN
        outboxService.failPublish(event.getEventId());
        //THEN
        Outbox outbox = outboxService.loadOutbox(event.getEventId());
        assertEquals(1, outbox.getCount());
    }

    @Test
    @DisplayName("특정 카운트 이상 되었을 때 skipped 활성화")
    void failPublishSkipped() {
        //GIVEN
        outboxService.create(topicId, event);
        //WHEN
        outboxService.failPublish(event.getEventId());
        outboxService.failPublish(event.getEventId());
        outboxService.failPublish(event.getEventId());
        //THEN
        Outbox outbox = outboxService.loadOutbox(event.getEventId());
        assertEquals(3, outbox.getCount());
        assertTrue(outbox.isSkipped());
    }

    @Test
    @DisplayName("정상적으로 발행 완료되었을 때 outbox상태 변경 - published true")
    void successPublish() {
        //GIVEN
        outboxService.create(topicId, event);
        //WHEN
        outboxService.successPublish(event.getEventId());
        //THEN
        Outbox outbox = outboxService.loadOutbox(event.getEventId());
        assertTrue(outbox.isPublished());
    }

    @Test
    @DisplayName("정상적으로 Message 가 발행되었을 때 publish가 true로 변경되는지")
    void successMessagePublish() throws InterruptedException {
        //GIVEN
        outboxService.create(topicId, event);
        //WHEN
        producerCluster.sendMessage(topicId, event);
        Thread.sleep(1000);
        //THEN
        Outbox outbox = outboxService.loadOutbox(event.getEventId());
        assertTrue(outbox.isPublished());
    }
}
