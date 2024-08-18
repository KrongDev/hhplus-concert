package com.hhplusconcert.domain.watingToken.stream;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
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
class WaitingTokenConsumerTest {
    @Autowired
    private WaitingTokenService waitingTokenService;
    @Autowired
    private KafkaProducerCluster producerCluster;
    @Autowired
    private TruncateTableComponent truncateTableComponent;

    private final String userId = "test_userId";
    private final String seriesId = "test_seriesId";

    @BeforeEach
    public void setUp() {
        //
        truncateTableComponent.truncateTable(() -> {}, "waiting_token");
    }

    @Test
    @DisplayName("결제완료 후 waitingToken 정상 삭제")
    void paymentConfirmedConsume() throws InterruptedException {
        this.waitingTokenService.create(userId, seriesId);
        PaymentConfirmed data = PaymentConfirmed.of(
                "",
                userId,
                "",
                "",
                seriesId,
                "",
                0,
                0,
                0,
                ""
        );
        producerCluster.sendMessage(PaymentConfirmed.topicId, data);

        Thread.sleep(1000);

        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> this.waitingTokenService.loadWaitingToken(userId, seriesId));

        assertEquals(ErrorType.TOKEN_NOT_FOUND, exception.getErrorType());
    }
}
