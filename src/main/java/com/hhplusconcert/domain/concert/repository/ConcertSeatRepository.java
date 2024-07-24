package com.hhplusconcert.domain.concert.repository;

import com.hhplusconcert.domain.concert.model.ConcertSeat;

import java.util.List;

public interface ConcertSeatRepository {
    void save(ConcertSeat concertSeat);
    void saveAll(List<ConcertSeat> concertSeatList);
    ConcertSeat findById(String seatId);
    List<ConcertSeat> findAllBySeriesId(String seriesId);
    List<ConcertSeat> findAllBySeriesIds(List<String> seriesIds);
}
