package com.hhplusconcert.domain.temporaryReservation.service;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporaryReservationService {
    //
    private final TemporaryReservationRepository temporaryReservationRepository;

    public String create(
            String userId,
            String concertId,
            String seriesId,
            int seatRow,
            int seatCol
    ) {
        TemporaryReservation temporaryReservation = TemporaryReservation.newInstance(userId, concertId, seriesId, seatRow, seatCol);
        this.temporaryReservationRepository.save(temporaryReservation);
        return temporaryReservation.getTemporaryReservationId();
    }

    public List<TemporaryReservation> loadTemporaryReservations(String userId) {
        //
        return this.temporaryReservationRepository.findByUserId(userId);
    }

    public TemporaryReservation loadTemporaryReservation(String temporaryReservationId) {
        //
        return this.temporaryReservationRepository.findByIdWithException(temporaryReservationId);
    }
}
