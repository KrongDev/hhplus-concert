package com.hhplusconcert.infra.temporaryReservation.orm;


import com.hhplusconcert.infra.temporaryReservation.orm.jpo.TemporaryReservationJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemporaryReservationJpoRepository extends JpaRepository<TemporaryReservationJpo, String> {
    //
    Optional<TemporaryReservationJpo> findByTemporaryReservationIdAndPaid(String temporaryReservationId, boolean paid);
    List<TemporaryReservationJpo> findAllByUserId(String userId);
}
