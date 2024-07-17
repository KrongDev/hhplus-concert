package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.common.exception.model.CustomGlobalException;
import com.hhplusconcert.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.repository.ConcertSeriesRepository;
import com.hhplusconcert.infra.concert.orm.ConcertSeriesJpoRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeriesJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertSeriesRepositoryImpl implements ConcertSeriesRepository {
    //
    private final ConcertSeriesJpoRepository concertSeriesJpoRepository;


    @Override
    public void save(ConcertSeries concertSeries) {
        //
        this.concertSeriesJpoRepository.save(new ConcertSeriesJpo(concertSeries));
    }

    @Override
    public ConcertSeries findByIdWithThrow(String seriesId) {
        Optional<ConcertSeriesJpo> jpo = this.concertSeriesJpoRepository.findById(seriesId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.CONCERT_SERIES_NOT_FOUND);
        return jpo.get().toDomain();
    }

    @Override
    public List<ConcertSeries> findByConcertIdAndNowReserving(String concertId, Long now) {
        List<ConcertSeriesJpo> jpos = this.concertSeriesJpoRepository.findByConcertIdAndReserveStartAtLessThanEqualAndReserveEndAtGreaterThanEqual(concertId, now, now);
        return jpos.stream().map(ConcertSeriesJpo::toDomain).toList();
    }
}
