package com.hhplusconcert.interfaces.consumer.reservation;

import com.hhplusconcert.application.reservation.facade.ReservationFlowFacade;
import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {
    //
    private final ReservationFlowFacade reservationFlowFacade;

    @KafkaListener(topics = {PaymentConfirmed.topicId}, groupId = "${concert.topic_groups.reservation}")
    public void paymentConfirmedConsume(String message) {
        PaymentConfirmed event = JsonUtil.fromJson(message, PaymentConfirmed.class);

        String temporaryReservationId = event.getTemporaryReservationId();
        String userId = event.getUserId();
        String paymentId = event.getPaymentId();
        String concertId = event.getConcertId();
        String title = event.getTitle();
        String seriesId = event.getSeriesId();
        String seatId = event.getSeatId();
        int seatRow = event.getSeatRow();
        int seatCol = event.getSeatCol();
        int price = event.getPrice();
        this.reservationFlowFacade.create(
                temporaryReservationId,
                userId,
                paymentId,
                concertId,
                title,
                seriesId,
                seatId,
                seatRow,
                seatCol,
                price
        );
    }
}
