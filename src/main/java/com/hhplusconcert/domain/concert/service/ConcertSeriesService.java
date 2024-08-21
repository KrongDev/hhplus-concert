package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.repository.ConcertSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConcertSeriesService {
    //
    private final ConcertSeriesRepository concertSeriesRepository;

    @Transactional
    public String create(
            String concertId,
            Long startAt,
            Long endAt,
            Long reserveStartAt,
            Long reserveEndAt
    ) {
            //
        ConcertSeries concertSeries = ConcertSeries.newInstance(
                concertId,
                startAt,
                endAt,
                reserveStartAt,
                reserveEndAt
        );
        this.concertSeriesRepository.save(concertSeries);
        return concertSeries.getSeriesId();
    }

    public ConcertSeries loadConcertSeries(String seriesId) {
        //
        ConcertSeries series =  this.concertSeriesRepository.findById(seriesId);
        if(Objects.isNull(series))
            throw new CustomGlobalException(ErrorType.CONCERT_SERIES_NOT_FOUND);
        return series;
    }

    public ConcertSeries loadConcertSeriesReservationAvailable(String seriesId) {
        //
        ConcertSeries concertSeries = this.loadConcertSeries(seriesId);
        concertSeries.validateReservationAvailable();
        return concertSeries;
    }

    public List<ConcertSeries> loadConcertSeriesByConcertIdAndNowReserving(String concertId, int page, int size) {
        //
        Long now = System.currentTimeMillis();
        return this.concertSeriesRepository.findByConcertIdAndNowReserving(concertId, now, page, size);
    }
}
