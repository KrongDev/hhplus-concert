package com.hhplusconcert.domain.concert.repository;

import com.hhplusconcert.domain.concert.model.Concert;

import java.util.List;

public interface ConcertRepository {
    void save(Concert concert);
    Concert findById(String concertId);
    List<Concert> findAll(int page, int size);
}
