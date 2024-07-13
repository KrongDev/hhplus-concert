package com.hhplusconcert.domain.reservation.repository;


import com.hhplusconcert.domain.reservation.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    void save(Reservation reservation);
    Reservation findByIdWithThrow(String id);
    List<Reservation> findAllByUserId(String userId);
}
