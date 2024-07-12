package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.infra.concert.orm.ConcertSeatJpoRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeatJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {
    //
    private final ConcertSeatJpoRepository concertSeatJpoRepository;


    @Override
    public void save(ConcertSeat concertSeat) {
        //
        this.concertSeatJpoRepository.save(new ConcertSeatJpo(concertSeat));
    }

    @Override
    public void saveAll(List<ConcertSeat> concertSeatList) {
        //
        this.concertSeatJpoRepository.saveAll(concertSeatList.stream().map(ConcertSeatJpo::new).toList());
    }

    @Override
    public List<ConcertSeat> findAllBySeriesId(String seriesId) {
        List<ConcertSeatJpo> jpos = this.concertSeatJpoRepository.findAllBySeriesId(seriesId);
        return jpos.stream().map(ConcertSeatJpo::toDomain).toList();
    }

    @Override
    public List<ConcertSeat> findAllBySeriesIds(List<String> seriesIds) {
        List<ConcertSeatJpo> jpos = this.concertSeatJpoRepository.findAllBySeatIdIn(seriesIds);
        return jpos.stream().map(ConcertSeatJpo::toDomain).toList();
    }

    @Override
    public ConcertSeat findByIdWithThrow(String seatId) {
        ConcertSeatJpo jpo = this.concertSeatJpoRepository.findById(seatId).orElseThrow();
        return jpo.toDomain();
    }
}
