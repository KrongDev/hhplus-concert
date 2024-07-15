package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.repository.ConcertSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

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
        return this.concertSeriesRepository.findById(seriesId);
    }

    public List<ConcertSeries> loadConcertSeriesByConcertIdAndNowReserving(String concertId) {
        //
        Long now = System.currentTimeMillis();
        return this.concertSeriesRepository.findByConcertIdAndNowReserving(concertId, now);
    }
}
