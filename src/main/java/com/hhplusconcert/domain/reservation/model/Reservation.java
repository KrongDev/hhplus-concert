package com.hhplusconcert.domain.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class Reservation implements Serializable {
    //
    private String reservationId;
    private String paymentId;
    private String userId;
    private String concertId;
    private String title;
    private String seriesId;
    private String seatId;
    private int seatRow;
    private int seatCol;
    private int price;

    private long createAt;

    public static Reservation newInstance(
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
        return Reservation.builder()
                .reservationId(temporaryReservationId)
                .paymentId(paymentId)
                .userId(userId)
                .concertId(concertId)
                .title(title)
                .seriesId(seriesId)
                .seatId(seatId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .price(price)
                .createAt(System.currentTimeMillis())
                .build();
    }
}
