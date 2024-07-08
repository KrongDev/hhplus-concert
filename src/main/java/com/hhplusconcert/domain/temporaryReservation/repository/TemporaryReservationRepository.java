package com.hhplusconcert.domain.temporaryReservation.repository;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;

import java.util.List;

public interface TemporaryReservationRepository {
    void save(TemporaryReservation temporaryReservation);
    List<TemporaryReservation> findByUserId(String userId);
    TemporaryReservation findByIdWithException(String temporaryReservationId);
}
