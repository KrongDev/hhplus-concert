package com.hhplusconcert.domain.concert.repository;

import com.hhplusconcert.domain.concert.model.ConcertSeat;

import java.util.List;

public interface ConcertSeatRepository {
    void save(ConcertSeat concertSeat);
    void saveAll(List<ConcertSeat> concertSeatList);
    List<ConcertSeat> findAllBySeriesId(String seriesId);
    ConcertSeat findByIdWithThrow(String seriesId, int row, int col);
}
