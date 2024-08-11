package com.hhplusconcert.domain.payment.event;

import lombok.Builder;

@Builder
public record PaymentConfirmed(
        String temporaryReservationId,
        String userId,
        String concertId,
        String title,
        String seriesId,
        String seatId,
        int seatRow,
        int seatCol,
        int price,

        String paymentId
) {

    public static PaymentConfirmed of(
            String temporaryReservationId,
            String userId,
            String concertId,
            String title,
            String seriesId,
            String seatId,
            int seatRow,
            int seatCol,
            int price,
            String paymentId
    ) {
        return PaymentConfirmed.builder()
                .temporaryReservationId(temporaryReservationId)
                .userId(userId)
                .concertId(concertId)
                .title(title)
                .seriesId(seriesId)
                .seatId(seatId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .price(price)
                .paymentId(paymentId)
                .build();
    }
}
