package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConcertService {
    //
    private final ConcertRepository concertRepository;

    @Transactional
    public String create(String userId, String title) {
        //
        Concert concert = Concert.newInstance(userId, title);
        this.concertRepository.save(concert);
        return concert.getConcertId();
    }

    public Concert loadConcert(String concertId) {
        //
        Concert concert = this.concertRepository.findById(concertId);
        if(Objects.isNull(concert))
            throw new CustomGlobalException(ErrorType.CONCERT_NOT_FOUND);
        return concert;
    }

    public List<Concert> loadConcerts(int page, int size) {
        //
        return this.concertRepository.findAll(page, size);
    }
}
