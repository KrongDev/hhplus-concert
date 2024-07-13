package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.repository.ConcertRepository;
import com.hhplusconcert.infra.concert.orm.ConcertJpoRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    //
    private final ConcertJpoRepository concertJpoRepository;

    @Override
    public void save(Concert concert){
        //
        this.concertJpoRepository.save(new ConcertJpo(concert));
    }

    @Override
    public Concert findById(String concertId) {
        ConcertJpo jpo = this.concertJpoRepository.findById(concertId).orElseThrow();
        return jpo.toDomain();
    }

    @Override
    public List<Concert> findAll() {
        //
        List<ConcertJpo> jpos =  this.concertJpoRepository.findAll();
        return jpos.stream().map(ConcertJpo::toDomain).toList();
    }
}
