package com.hhplusconcert.application.temporaryReservation.facade;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryReservationFlowFacade {
    //
    private final ConcertService concertService;
    private final ConcertSeriesService concertSeriesService;
    private final ConcertSeatService concertSeatService;
    private final TemporaryReservationService temporaryReservationService;

    public String createTemporaryReservation(
            String userId,
            String concertId,
            String seriesId,
            int seatRow,
            int seatCol
    ) throws IllegalAccessException {
        // 콘서트 리스트 조회
        ConcertSeries concertSeries = this.concertSeriesService.loadConcertSeries(seriesId);
        if(!concertSeries.isReservationAvailable())
            throw new IllegalAccessException("신청가능 시간이 아닙니다.");
        // 콘서트 좌석 조회
        ConcertSeat concertSeat = this.concertSeatService.loadConcertSeatById(seriesId, seatRow, seatCol);
        if(concertSeat.isReserved())
            throw new IllegalAccessException("신청가능한 좌석이 아닙니다.");
        Concert concert = this.concertService.loadConcert(concertId);
        // 좌석 예약
        this.concertSeatService.reserveSeat(seriesId, seatRow, seatCol);
        // 임시 예약 생성
        return this.temporaryReservationService.create(userId, concertId, concert.getTitle() , seriesId, seatRow, seatCol, concertSeat.getPrice());
    }
}
