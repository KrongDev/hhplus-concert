package com.hhplusconcert.infra.temporaryReservation.orm;


import com.hhplusconcert.infra.temporaryReservation.orm.jpo.TemporaryReservationJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemporaryReservationJpoRepository extends JpaRepository<TemporaryReservationJpo, String> {
    //
    List<TemporaryReservationJpo> findAllByUserId(String userId);
}
