package com.hhplusconcert.domain.temporaryReservation.service;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporaryReservationService {
    //
    private final TemporaryReservationRepository temporaryReservationRepository;

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
        TemporaryReservation temporaryReservation = TemporaryReservation.newInstance(userId, concertId, title, seriesId, seatId, seatRow, seatCol, price);
        this.temporaryReservationRepository.save(temporaryReservation);
        return temporaryReservation.getTemporaryReservationId();
    }

    public List<TemporaryReservation> loadTemporaryReservations(String userId) {
        //
        return this.temporaryReservationRepository.findByUserId(userId);
    }

    public List<TemporaryReservation> loadExpiredTemporaryReservations() {
        //
        long now = System.currentTimeMillis();
        return this.temporaryReservationRepository.findAllByDeleteAt(now);
    }

    public TemporaryReservation loadTemporaryReservation(String temporaryReservationId) {
        //
        return this.temporaryReservationRepository.findByIdWithException(temporaryReservationId);
    }

    @Transactional
    public void payReservation(String temporaryReservationId) {
        //
        TemporaryReservation temporaryReservation = this.temporaryReservationRepository.findByIdAndNotPaidWithException(temporaryReservationId);
        temporaryReservation.pay();
        this.temporaryReservationRepository.save(temporaryReservation);
    }

    @Transactional
    public void deleteIds(List<String> temporaryReservationIds) {
        //
        this.temporaryReservationRepository.deleteByIds(temporaryReservationIds);
    }
}
