package com.hhplusconcert.application.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeatInfo {
    private String sheetId;
    private String concertId;
    private String seriesId;
    private int row;
    private int col;
    private int price;
    private boolean reserved;

    public static ConcertSeatInfo sample() {
        return new ConcertSeatInfo(
                "",
                "",
                "",
                1,
                1,
                10000,
                false
        );
    }
}
