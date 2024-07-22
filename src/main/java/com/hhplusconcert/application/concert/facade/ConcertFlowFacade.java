package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class ConcertFlowFacade {
    //
    private final ConcertService concertService;
    private final ConcertSeriesService concertSeriesService;
    private final ConcertSeatService concertSeatService;

    @Transactional
    public String createConcert( String userId, String title ) {
        //
        return this.concertService.create(userId, title);
    }

    @Transactional
    public String createConcertSeries(
            String concertId,
            Long startAt,
            Long endAt,
            Long reserveStartAt,
            Long reserveEndAt,
            int maxRow,
            int maxSeat
    ) {
        Concert concert = this.concertService.loadConcert(concertId);
        String seriesId = this.concertSeriesService.create(concert.getConcertId(), startAt, endAt, reserveStartAt, reserveEndAt);
        this.concertSeatService.createAll(seriesId, maxRow, maxSeat);
        return seriesId;
    }


}
