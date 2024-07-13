package com.hhplusconcert.schedule;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemporaryReservationScheduler {
    //
    private final TemporaryReservationService temporaryReservationService;
    private final ConcertSeatService concertSeatService;

    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void timeLimitTemporaryReservation () {
        //
        List<TemporaryReservation> temporaryReservations = this.temporaryReservationService.loadExpiredTemporaryReservations();
        List<String> temporaryReservationIds = temporaryReservations.stream().map(TemporaryReservation::getTemporaryReservationId).toList();
        this.temporaryReservationService.deleteIds(temporaryReservationIds);
        List<String> concertSeatIds = temporaryReservations.stream().map(TemporaryReservation::getSeatId).toList();
        List<ConcertSeat> seats = this.concertSeatService.loadConcertSeatsBySeries(concertSeatIds);
        seats.forEach(ConcertSeat::unreserve);
        this.concertSeatService.updateAll(seats);
    }
}
