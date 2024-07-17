package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.common.exception.model.CustomGlobalException;
import com.hhplusconcert.common.exception.model.vo.ErrorType;
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
    public Concert findByIdWithThrow(String concertId) {
        Optional<ConcertJpo> jpo = this.concertJpoRepository.findById(concertId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.CONCERT_NOT_FOUND);
        return jpo.get().toDomain();
    }

    @Override
    public List<Concert> findAll() {
        //
        List<ConcertJpo> jpos =  this.concertJpoRepository.findAll();
        return jpos.stream().map(ConcertJpo::toDomain).toList();
    }
}
