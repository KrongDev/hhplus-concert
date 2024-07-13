package com.hhplusconcert.application.concert.dto;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetail {
    //
    private String concertId;
    private String title;
    private String seriesId;
    private Long startAt;
    private Long endAt;
    private Long reserveStartAt;
    private Long reserveEndAt;
    private List<ConcertSeat> seatList;

    public ConcertDetail(Concert concert, ConcertSeries series, List<ConcertSeat> seats) {
        this.concertId  = concert.getConcertId();
        this.title = concert.getTitle();
        this.seriesId = series.getSeriesId();
        this.startAt = series.getStartAt();
        this.endAt = series.getEndAt();
        this.reserveStartAt = series.getReserveStartAt();
        this.reserveEndAt = series.getReserveEndAt();
        this.seatList = seats;
    }
}
