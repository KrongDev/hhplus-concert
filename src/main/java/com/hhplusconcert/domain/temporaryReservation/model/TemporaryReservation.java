package com.hhplusconcert.domain.temporaryReservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryReservation {
    private String temporaryReservationId;
    private String userId;
    private String concertId;
    private String seriesId;
    private int seatRow;
    private int seatCol;
    private Long createAt;
    private Long deleteAt;

    public static TemporaryReservation newInstance(
        String userId,
        String concertId,
        String seriesId,
        int seatRow,
        int seatCol
    ) {
        String newId = UUID.randomUUID().toString();
        return TemporaryReservation.builder()
                .temporaryReservationId(newId)
                .userId(userId)
                .concertId(concertId)
                .seriesId(seriesId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .createAt(System.currentTimeMillis())
                .deleteAt(0L)
                .build();
    }
}
