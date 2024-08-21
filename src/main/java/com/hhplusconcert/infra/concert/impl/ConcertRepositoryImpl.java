package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.repository.ConcertRepository;
import com.hhplusconcert.infra.concert.orm.ConcertJpaRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    //
    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public void save(Concert concert){
        //
        this.concertJpaRepository.save(new ConcertJpo(concert));
    }

    @Override
    public Concert findById(String concertId) {
        Optional<ConcertJpo> jpo = this.concertJpaRepository.findById(concertId);
        return jpo.map(ConcertJpo::toDomain).orElse(null);
    }

    @Override
    public List<Concert> findAll(int page, int size) {
        //
        Pageable pageable = PageRequest.of(page, size, Sort.by(ASC, "concertId"));
        Page<ConcertJpo> jpos =  this.concertJpaRepository.findAll(pageable);
        return jpos.getContent().stream().map(ConcertJpo::toDomain).toList();
    }
}
