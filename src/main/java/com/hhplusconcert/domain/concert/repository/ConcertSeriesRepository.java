package com.hhplusconcert.domain.concert.repository;

import com.hhplusconcert.domain.concert.model.ConcertSeries;


import java.util.List;

public interface ConcertSeriesRepository {
    void save(ConcertSeries concertSeries);
    ConcertSeries findById(String seriesId);
    List<ConcertSeries> findByConcertIdAndNowReserving(String concertId, Long now, int page, int size);
}
