package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
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
        String seriesId = this.concertSeriesService.create(concertId, startAt, endAt, reserveStartAt, reserveEndAt);
        this.concertSeatService.createAll(seriesId, maxRow, maxSeat);
        return seriesId;
    }


}
