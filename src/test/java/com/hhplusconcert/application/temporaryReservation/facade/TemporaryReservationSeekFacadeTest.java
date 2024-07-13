package com.hhplusconcert.application.temporaryReservation.facade;

import com.hhplusconcert.application.temporaryReservation.dto.TemporaryReservationInfo;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.model.vo.ConcertSeriesStatus;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TemporaryReservationSeekFacadeTest {

    @Mock
    private TemporaryReservationService temporaryReservationService;
    @Mock
    private ConcertService concertService;
    @Mock
    private ConcertSeriesService concertSeriesService;
    @InjectMocks
    private TemporaryReservationSeekFacade temporaryReservationSeekFacade;

    private final String temporaryReservationId = "test_temporary_reservation_id";
    private final String userId = "test_userId";
    private final String concertId = "test_concert_id";
    private final String title = "test_title";
    private final String seriesId = "test_series_id";
    private final int seatRow = 0;
    private final int seatCol = 1;
    private final long startAt = System.currentTimeMillis();
    private final long endAt = System.currentTimeMillis() + 1000000;

    @Test
    @DisplayName("임시 예약 정보 조회 테스트")
    void loadTemporaryReservation() {
        //GIVEN
        when(this.temporaryReservationService.loadTemporaryReservation(temporaryReservationId)).thenReturn(new TemporaryReservation(temporaryReservationId,0, userId, concertId, title, seriesId, seatRow, seatCol, 10000, 0L, 0L, false));
        when(this.concertService.loadConcert(concertId)).thenReturn(new Concert(concertId, title, userId, System.currentTimeMillis()));
        when(this.concertSeriesService.loadConcertSeries(seriesId)).thenReturn(new ConcertSeries(seriesId, concertId, startAt, endAt, startAt, endAt, ConcertSeriesStatus.READY, startAt));

        //WHEN
        TemporaryReservationInfo info = this.temporaryReservationSeekFacade.loadTemporaryReservation(temporaryReservationId);

        //THEN
        assertEquals(concertId, info.getConcertId());
        assertEquals(title, info.getTitle());
        assertEquals(userId, info.getUserId());
        assertEquals(seriesId, info.getSeriesId());
        assertEquals(seatRow, info.getRow());
        assertEquals(seatCol, info.getCol());
        assertEquals(startAt, info.getStartAt());
        assertEquals(endAt, info.getEndAt());
    }
}
