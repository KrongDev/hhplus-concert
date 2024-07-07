package com.hhplusconcert.infra.concert.orm;

import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeatJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpoRepository extends JpaRepository<ConcertSeatJpo, ConcertSeatJpo.SeatKey> {
    List<ConcertSeatJpo> findAllBySeatIdSeriesId(String seriesId);
}
