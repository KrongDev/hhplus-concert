package com.hhplusconcert.application.reservation.facade;

import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationSeekFacade {
    //
    private final ReservationService reservationService;

    @Cacheable(cacheNames = "reservation", key="#reservationId", cacheManager = "cacheManager")
    public Reservation loadReservation(String reservationId) {
        //
        return this.reservationService.loadReservation(reservationId);
    }

    @Cacheable(cacheNames = "reservation", key = "#userId", cacheManager = "cacheManager")
    public List<Reservation> loadReservationsByUserId(String userId) {
        //
        return this.reservationService.loadAllReservationsByUserId(userId);
    }
}
