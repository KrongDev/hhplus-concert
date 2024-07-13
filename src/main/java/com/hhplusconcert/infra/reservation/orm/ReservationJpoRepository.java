package com.hhplusconcert.infra.reservation.orm;

import com.hhplusconcert.infra.reservation.orm.jpo.ReservationJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationJpoRepository extends JpaRepository<ReservationJpo, String> {
    //
    List<ReservationJpo> findAllByUserId(String userId);
}
