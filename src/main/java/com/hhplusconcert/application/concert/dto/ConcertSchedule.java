package com.hhplusconcert.application.concert.dto;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSchedule implements Serializable {
    //
    private String concertId;
    private String title;
    private List<ConcertSeries> seriesList;

    public ConcertSchedule(Concert concert, List<ConcertSeries> seriesList) {
        this.concertId = concert.getConcertId();
        this.title = concert.getTitle();
        this.seriesList = seriesList;
    }
}
