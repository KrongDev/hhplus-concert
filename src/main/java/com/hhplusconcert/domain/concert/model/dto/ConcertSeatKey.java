package com.hhplusconcert.domain.concert.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeatKey {
    //
    private String seriesId;
    private int seatRow;
    private int seatCol;
}
