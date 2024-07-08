package com.hhplusconcert.application.temporaryReservation.dto;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemporaryReservationInfo {
    //
    private String userId;
    private String concertId;
    private String title;
    private String seriesId;
    private Long startAt;
    private Long endAt;
    private int row;
    private int col;
    private Long createAt;

    public static TemporaryReservationInfo of(Concert concert, ConcertSeries concertSeries, TemporaryReservation temporaryReservation) {
        return TemporaryReservationInfo.builder()
                .userId(temporaryReservation.getUserId())
                .concertId(concert.getConcertId())
                .title(concert.getTitle())
                .seriesId(concertSeries.getSeriesId())
                .startAt(concertSeries.getStartAt())
                .endAt(concertSeries.getEndAt())
                .row(temporaryReservation.getSeatRow())
                .col(temporaryReservation.getSeatCol())
                .createAt(temporaryReservation.getCreateAt())
                .build();
    }
}
