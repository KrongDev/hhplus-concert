package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 3, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class PointHistoryConsumerTest {
    @Autowired
    private PointHistoryService pointHistoryService;
    @Autowired
    private KafkaProducerCluster producerCluster;
    @Autowired
    private TruncateTableComponent truncateTableComponent;

    private final String userId = "test_userId";

    @BeforeEach
    public void setUp() {
        truncateTableComponent.truncateTable(() -> {}, "point_history");
    }

    @Test
    @DisplayName("Point Charge 후 후처리가 정상적으로 이뤄지는지 검증")
    void chargedPointConsume() throws InterruptedException {
        ChargedPoint data = ChargedPoint.of(userId, 10000);

        producerCluster.sendMessage(data);
        Thread.sleep(500);
        List<PointHistory> histories = this.pointHistoryService.loadPointHistories(userId);
        assertEquals(1, histories.size());
        assertEquals(data.getRequestUserId(), histories.get(0).getUserId());
        assertEquals(PointHistoryStatus.CHARGE, histories.get(0).getStatus());
        assertEquals(data.getAmount(), histories.get(0).getAmount());
    }

    @Test
    @DisplayName("Point Use 후 후처리가 정상적으로 이뤄지는지 검증")
    void usedPointConsume() throws InterruptedException {
        String paymentId = "test_paymentId";
        UsedPoint data = UsedPoint.of(userId, paymentId,  10000);

        producerCluster.sendMessage(data);
        Thread.sleep(500);
        List<PointHistory> histories = this.pointHistoryService.loadPointHistories(userId);
        assertEquals(1, histories.size());
        assertEquals(data.getRequestUserId(), histories.get(0).getUserId());
        assertEquals(data.getPaymentId(), histories.get(0).getPaymentId());
        assertEquals(PointHistoryStatus.USE, histories.get(0).getStatus());
        assertEquals(data.getAmount(), histories.get(0).getAmount());
    }
}
