package com.hhplusconcert.domain.reservation.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {
    //
    private final ReservationRepository reservationRepository;

    @Transactional
    public String create(
            String temporaryReservationId,
            String userId,
            String paymentId,
            String concertId,
            String title,
            String seriesId,
            String seatId,
            int seatRow,
            int seatCol,
            int price
    ) {
        //
        Reservation reservation = Reservation.newInstance(temporaryReservationId, userId, paymentId, concertId, title, seriesId, seatId, seatRow, seatCol, price);
        reservationRepository.save(reservation);
        return reservation.getReservationId();
    }

    public Reservation loadReservation(String reservationId) {
        //
        Reservation reservation = this.reservationRepository.findById(reservationId);
        if(Objects.isNull(reservation))
            throw new CustomGlobalException(ErrorType.RESERVATION_NOT_FOUND);
        return reservation;
    }

    public List<Reservation> loadAllReservationsByUserId(String userId) {
        //
        return this.reservationRepository.findAllByUserId(userId);
    }
}
