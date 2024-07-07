package com.hhplusconcert.domain.concert.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat {
    //
    private String seriesId;
    private int seatRow;
    private int seatCol;
    private int entityVersion;
    private int seatIndex;
    private int price;
    private boolean reserved;

    public static ConcertSeat newInstance(
            String seriesId,
            int row,
            int col,
            int index,
            int price
    ) {
        return ConcertSeat.builder()
                .seriesId(seriesId)
                .seatRow(row)
                .seatCol(col)
                .seatIndex(index)
                .price(price)
                .build();
    }
}
