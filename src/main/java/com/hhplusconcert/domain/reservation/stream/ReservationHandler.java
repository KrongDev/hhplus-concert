package com.hhplusconcert.domain.reservation.stream;

import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import com.hhplusconcert.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationHandler {
    //
    private final ReservationService reservationService;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(PaymentConfirmed event) {
        //
        String temporaryReservationId = event.temporaryReservationId();
        String userId = event.userId();
        String paymentId = event.paymentId();
        String concertId = event.concertId();
        String title = event.title();
        String seriesId = event.seriesId();
        String seatId = event.seatId();
        int seatRow = event.seatRow();
        int seatCol = event.seatCol();
        int price = event.price();
        this.reservationService.create(
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
