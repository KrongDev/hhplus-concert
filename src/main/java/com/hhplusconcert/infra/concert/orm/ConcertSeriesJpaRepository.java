package com.hhplusconcert.infra.concert.orm;

import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeriesJpo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ConcertSeriesJpaRepository extends JpaRepository<ConcertSeriesJpo, String> {
    List<ConcertSeriesJpo> findByConcertIdAndReserveStartAtLessThanEqualAndReserveEndAtGreaterThanEqual(String concertId, Long reserveStartAt, Long reserveEndAt, Pageable pageable);
}
