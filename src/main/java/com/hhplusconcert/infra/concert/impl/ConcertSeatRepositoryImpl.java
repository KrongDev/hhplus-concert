package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.infra.concert.orm.ConcertSeatJpaRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeatJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {
    //
    private final ConcertSeatJpaRepository concertSeatJpaRepository;


    @Override
    public void save(ConcertSeat concertSeat) {
        //
        this.concertSeatJpaRepository.save(new ConcertSeatJpo(concertSeat));
    }

    @Override
    public void saveAll(List<ConcertSeat> concertSeatList) {
        //
        this.concertSeatJpaRepository.saveAll(concertSeatList.stream().map(ConcertSeatJpo::new).toList());
    }

    @Override
    public List<ConcertSeat> findAllBySeriesId(String seriesId) {
        List<ConcertSeatJpo> jpos = this.concertSeatJpaRepository.findAllBySeriesId(seriesId);
        return jpos.stream().map(ConcertSeatJpo::toDomain).toList();
    }

    @Override
    public List<ConcertSeat> findAllBySeriesIds(List<String> seriesIds) {
        List<ConcertSeatJpo> jpos = this.concertSeatJpaRepository.findAllBySeatIdIn(seriesIds);
        return jpos.stream().map(ConcertSeatJpo::toDomain).toList();
    }

    @Override
    public ConcertSeat findByIdWithThrow(String seatId) {
        Optional<ConcertSeatJpo> jpo = this.concertSeatJpaRepository.findById(seatId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.CONCERT_SEAT_NOT_FOUND);
        return jpo.get().toDomain();
    }
}
