package com.hhplusconcert.application.temporaryReservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryReservation {
    private String temporaryReservationId;
    private String userId;
    private String concertId;
    private String seriesId;
    private String seatId;
    private Long createAt;
    private Long deleteAt;

    public static TemporaryReservation sample() {
        return new TemporaryReservation(
                "",
                "",
                "",
                "",
                "",
                System.currentTimeMillis(),
                null
        );
    }
}
