package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.repository.ConcertSeriesRepository;
import com.hhplusconcert.infra.concert.orm.ConcertSeriesJpaRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeriesJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Repository
@RequiredArgsConstructor
public class ConcertSeriesRepositoryImpl implements ConcertSeriesRepository {
    //
    private final ConcertSeriesJpaRepository concertSeriesJpaRepository;

    @Override
    public void save(ConcertSeries concertSeries) {
        //
        this.concertSeriesJpaRepository.save(new ConcertSeriesJpo(concertSeries));
    }

    @Override
    public ConcertSeries findById(String seriesId) {
        Optional<ConcertSeriesJpo> jpo = this.concertSeriesJpaRepository.findById(seriesId);
        return jpo.map(ConcertSeriesJpo::toDomain).orElse(null);
    }

    @Override
    public List<ConcertSeries> findByConcertIdAndNowReserving(String concertId, Long now, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(ASC, "concertId"));
        List<ConcertSeriesJpo> jpos = this.concertSeriesJpaRepository.findByConcertIdAndReserveStartAtLessThanEqualAndReserveEndAtGreaterThanEqual(concertId, now, now, pageable);
        return jpos.stream().map(ConcertSeriesJpo::toDomain).toList();
    }
}
