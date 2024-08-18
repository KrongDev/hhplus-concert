package com.hhplusconcert.domain.outbox.scheduler;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.common.Event;
import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.repository.OutboxRepository;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class OutboxSchedulerTest {

    @Autowired
    private TruncateTableComponent truncateTableComponent;
    @Autowired
    private OutboxRepository outboxRepository;

    private final Event message = ChargedPoint.of("test_userId", 10000);

    @BeforeEach
    public void setUp() {
        //
        truncateTableComponent.truncateTable(() -> {}, "outbox");
    }

    @Test
    @DisplayName("특정 시간 이내 발행된 Message들 재발행 검증 - 정상적으로 재발행되는지 테스트")
    void republishFailedMessages() throws InterruptedException {
        //GIVEN
        String payload = JsonUtil.toJson(message);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        long now = calendar.getTimeInMillis();
        Outbox outbox =  Outbox.builder()
                .id(message.getEventId())
                .topic(message.getClass().getSimpleName())
                .classPath(message.getClass().getName())
                .payload(payload)
                .createdAt(now)
                .publishedAt(now)
                .build();
        outboxRepository.save(outbox);
        //WHEN
        Thread.sleep(3000);
        //THEN
        Outbox nowOutBox = outboxRepository.findOutbox(message.getEventId());
        assertTrue(nowOutBox.isPublished());
    }
}
