package com.hhplusconcert.domain.reservation.stream;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.config.kafka.KafkaProducerCluster;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class ReservationConsumerTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private KafkaProducerCluster producerCluster;
    @Autowired
    private TruncateTableComponent truncateTableComponent;

    private final String temporaryReservationId = "test_temporaryReservationId";
    private final String userId = "test_userId";
    private final String concertId = "test_concertId";
    private final String title = "test_title";
    private final String seriesId = "test_seriesId";
    private final String seatId = "test_seatId";
    private final int seatRow = 0;
    private final int seatCol = 0;
    private final int price = 0;
    private final String paymentId = "test_paymentId";

    @BeforeEach
    public void setUp() {
        //
        truncateTableComponent.truncateTable(() -> {}, "reservation");
    }

    @Test
    @DisplayName("결제완료 후 reservation 정상 생성 검증")
    void paymentConfirmedConsume() throws InterruptedException {
        PaymentConfirmed data = PaymentConfirmed.of(
                temporaryReservationId,
                userId,
                concertId,
                title,
                seriesId,
                seatId,
                seatRow,
                seatCol,
                price,
                paymentId
        );
        producerCluster.sendMessage(PaymentConfirmed.topicId, data);

        Thread.sleep(1000);

        Reservation reservation = this.reservationService.loadReservation(temporaryReservationId);
        assertEquals(temporaryReservationId, reservation.getReservationId());
        assertEquals(userId, reservation.getUserId());
        assertEquals(concertId, reservation.getConcertId());
        assertEquals(title, reservation.getTitle());
        assertEquals(seriesId, reservation.getSeriesId());
        assertEquals(seatId, reservation.getSeatId());
        assertEquals(seatRow, reservation.getSeatRow());
        assertEquals(seatCol, reservation.getSeatCol());
        assertEquals(price, reservation.getPrice());
        assertEquals(paymentId, reservation.getPaymentId());
    }
}
