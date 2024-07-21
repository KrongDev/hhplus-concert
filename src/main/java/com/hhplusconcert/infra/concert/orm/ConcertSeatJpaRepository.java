package com.hhplusconcert.infra.concert.orm;

import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeatJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatJpo, String> {
    List<ConcertSeatJpo> findAllBySeriesId(String seriesId);
    List<ConcertSeatJpo> findAllBySeatIdIn(List<String> seatIds);
}
