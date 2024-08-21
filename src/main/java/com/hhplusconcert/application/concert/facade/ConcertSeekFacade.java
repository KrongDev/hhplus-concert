package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.application.concert.dto.ConcertDetail;
import com.hhplusconcert.application.concert.dto.ConcertSchedule;
import com.hhplusconcert.application.concert.dto.query.ConcertListQuery;
import com.hhplusconcert.application.concert.dto.query.ConcertSeriesListQuery;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertSeekFacade {
    //
    private final ConcertService concertService;
    private final ConcertSeriesService concertSeriesService;
    private final ConcertSeatService concertSeatService;

    @Cacheable(cacheNames = "concert", cacheManager = "cacheManager")
    public List<Concert> loadConcerts(ConcertListQuery query) {
        //
        return this.concertService.loadConcerts(query.page(), query.size());
    }

    @Cacheable(cacheNames = "concertSeries", key = "#query", cacheManager = "cacheManager")
    public ConcertSchedule loadConcertSeries(ConcertSeriesListQuery query) {
        //
        Concert concert = this.concertService.loadConcert(query.concertId());
        List<ConcertSeries> series = this.concertSeriesService.loadConcertSeriesByConcertIdAndNowReserving(concert.getConcertId(), query.page(), query.size());
        return new ConcertSchedule(concert, series);
    }

    public ConcertDetail loadConcertSeats(String seriesId) {
        //
        ConcertSeries series = this.concertSeriesService.loadConcertSeries(seriesId);
        Concert concert = this.concertService.loadConcert(series.getConcertId());
        List<ConcertSeat> seats = this.concertSeatService.loadConcertSeatsBySeries(seriesId);
        return new ConcertDetail(concert, series, seats);
    }
}
