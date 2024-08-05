package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.application.concert.dto.ConcertDetail;
import com.hhplusconcert.application.concert.dto.ConcertSchedule;
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
    public List<Concert> loadConcerts() {
        //
        return this.concertService.loadConcerts();
    }

    @Cacheable(cacheNames = "concertSeries", key = "#concertId", cacheManager = "cacheManager")
    public ConcertSchedule loadConcertSeries(String concertId) {
        //
        Concert concert = this.concertService.loadConcert(concertId);
        List<ConcertSeries> series = this.concertSeriesService.loadConcertSeriesByConcertIdAndNowReserving(concertId);
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
