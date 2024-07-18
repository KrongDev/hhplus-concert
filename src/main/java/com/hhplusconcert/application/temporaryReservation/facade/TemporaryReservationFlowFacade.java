package com.hhplusconcert.application.temporaryReservation.facade;

import com.hhplusconcert.common.annotation.LoggingPoint;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemporaryReservationFlowFacade {
    //
    private final ConcertService concertService;
    private final ConcertSeriesService concertSeriesService;
    private final ConcertSeatService concertSeatService;
    private final TemporaryReservationService temporaryReservationService;

    @LoggingPoint
    @Transactional
    public String createTemporaryReservation(
            String userId,
            String concertId,
            String seriesId,
            String seatId
    ) {
        Concert concert = this.concertService.loadConcert(concertId);
        // 콘서트 시리즈 조회
        ConcertSeries concertSeries = this.concertSeriesService.loadConcertSeriesReservationAvailable(seriesId);
        // 콘서트 좌석 조회
        ConcertSeat concertSeat = this.concertSeatService.loadConcertSeatById(seatId);
        // 좌석 예약
        this.concertSeatService.reserveSeat(concertSeat.getSeatId());
        // 임시 예약 생성
        return this.temporaryReservationService.create(
                userId,
                concert.getConcertId(),
                concert.getTitle(),
                concertSeries.getSeriesId(),
                concertSeat.getSeatId(),
                concertSeat.getSeatRow(),
                concertSeat.getSeatCol(),
                concertSeat.getPrice()
        );
    }

    @Transactional
    public void timeLimitTemporaryReservation() {
        //
        List<TemporaryReservation> temporaryReservations = this.temporaryReservationService.loadExpiredTemporaryReservations();
        List<String> temporaryReservationIds = temporaryReservations.stream().map(TemporaryReservation::getTemporaryReservationId).toList();
        this.temporaryReservationService.deleteIds(temporaryReservationIds);
        List<String> concertSeatIds = temporaryReservations.stream().map(TemporaryReservation::getSeatId).toList();
        List<ConcertSeat> seats = this.concertSeatService.loadConcertSeatsBySeries(concertSeatIds);
        seats.forEach(ConcertSeat::unReserve);
        this.concertSeatService.updateAll(seats);
    }
}
