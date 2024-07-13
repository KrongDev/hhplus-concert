package com.hhplusconcert.domain.reservation.service;

import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    //
    private final ReservationRepository reservationRepository;

    @Transactional
    public String create(
            String userId,
            String concertId,
            String title,
            String seriesId,
            String seatId,
            int seatRow,
            int seatCol,
            int price
    ) {
        //
        Reservation reservation = Reservation.newInstance(userId, concertId, title, seriesId, seatId, seatRow, seatCol, price);
        reservationRepository.save(reservation);
        return reservation.getReservationId();
    }

    public Reservation loadReservation(String reservationId) {
        //
        return this.reservationRepository.findByIdWithThrow(reservationId);
    }

    public List<Reservation> loadAllReservationsByUserId(String userId) {
        //
        return this.reservationRepository.findAllByUserId(userId);
    }
}
