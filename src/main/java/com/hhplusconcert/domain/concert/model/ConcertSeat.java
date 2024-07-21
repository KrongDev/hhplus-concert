package com.hhplusconcert.domain.concert.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat {
    //
    private String seatId;
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
        String newId = UUID.randomUUID().toString();
        return ConcertSeat.builder()
                .seatId(newId)
                .seriesId(seriesId)
                .seatRow(row)
                .seatCol(col)
                .seatIndex(index)
                .price(price)
                .build();
    }

    public void reserve() {
        //
        if(this.reserved)
            throw new CustomGlobalException(ErrorType.SEAT_ALREADY_RESERVED);
        this.reserved = true;
    }

    public void unReserve() {
        //
        this.reserved = false;
    }
}
