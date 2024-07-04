package com.hhplusconcert.application.temporaryReservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryReservation {
    private String temporaryReservationId;
    private String userId;
    private String concertId;
    private String seriesId;
    private String sheetId;
    private LocalDateTime createAt;
    private LocalDateTime deleteAt;

    public static TemporaryReservation sample() {
        return new TemporaryReservation(
                "",
                "",
                "",
                "",
                "",
                LocalDateTime.now(),
                null
        );
    }
}
