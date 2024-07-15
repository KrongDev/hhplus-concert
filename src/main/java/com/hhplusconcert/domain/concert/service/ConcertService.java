package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return this.concertRepository.findById(concertId);
    }

    public List<Concert> loadConcerts() {
        //
        return this.concertRepository.findAll();
    }
}
