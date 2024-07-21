package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.repository.ConcertRepository;
import com.hhplusconcert.infra.concert.orm.ConcertJpoRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        Optional<ConcertJpo> jpo = this.concertJpoRepository.findById(concertId);
        return jpo.map(ConcertJpo::toDomain).orElse(null);
    }

    @Override
    public List<Concert> findAll() {
        //
        List<ConcertJpo> jpos =  this.concertJpoRepository.findAll();
        return jpos.stream().map(ConcertJpo::toDomain).toList();
    }
}
