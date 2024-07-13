package com.hhplusconcert.domain.temporaryReservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryReservation {
    private String temporaryReservationId;
    private int entityVersion;
    private String userId;
    private String concertId;
    private String title;
    private String seriesId;
    private String seatId;
    private int seatRow;
    private int seatCol;
    private int price;
    private Long createAt;
    private Long deleteAt;
    private boolean paid;

    public static TemporaryReservation newInstance(
        String userId,
        String concertId,
        String title,
        String seriesId,
        String seatId,
        int seatRow,
        int seatCol,
        int price
    ) {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        calendar.add(Calendar.MINUTE, 5);
        String newId = UUID.randomUUID().toString();
        return TemporaryReservation.builder()
                .temporaryReservationId(newId)
                .userId(userId)
                .concertId(concertId)
                .title(title)
                .seriesId(seriesId)
                .seatId(seatId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .price(price)
                .createAt(now)
                .deleteAt(calendar.getTimeInMillis())
                .build();
    }

    public void pay() {
        this.paid = true;
    }
}
