package com.hhplusconcert.application.reservation.facade;

import com.hhplusconcert.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFlowFacade {
    //
    private final ReservationService reservationService;

    @Transactional
    public String create(
            String temporaryReservationId,
            String userId,
            String paymentId,
            String concertId,
            String title,
            String seriesId,
            String seatId,
            int seatRow,
            int seatCol,
            int price
    ) {
        //
        return this.reservationService.create(
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
