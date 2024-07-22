package com.hhplusconcert.application.temporaryReservation.facade;

import com.hhplusconcert.application.temporaryReservation.dto.TemporaryReservationInfo;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemporaryReservationSeekFacade {
    //
    private final TemporaryReservationService temporaryReservationService;
    private final ConcertService concertService;
    private final ConcertSeriesService concertSeriesService;

    public List<TemporaryReservation> loadTemporaryReservations(String userId) {
        //
        return this.temporaryReservationService.loadTemporaryReservations(userId);
    }

    public TemporaryReservationInfo loadTemporaryReservation(String temporaryReservationId) {
        //
        TemporaryReservation temporaryReservation = this.temporaryReservationService.loadTemporaryReservation(temporaryReservationId);
        Concert concert = this.concertService.loadConcert(temporaryReservation.getConcertId());
        ConcertSeries series = this.concertSeriesService.loadConcertSeries(temporaryReservation.getSeriesId());
        return TemporaryReservationInfo.of(concert, series, temporaryReservation);
    }
}
