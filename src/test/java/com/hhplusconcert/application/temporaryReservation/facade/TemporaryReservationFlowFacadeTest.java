package com.hhplusconcert.application.temporaryReservation.facade;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TemporaryReservationFlowFacadeTest {
    @Autowired
    private TemporaryReservationFlowFacade temporaryReservationFlowFacade;
    @Autowired
    private ConcertService concertService;
    @Autowired
    private ConcertSeriesService concertSeriesService;
    @Autowired
    private TemporaryReservationRepository temporaryReservationRepository;
    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    private final String userId = "test_userId";

    @Test
    @Order(1)
    @DisplayName("콘서트가 없을 경우 에러")
    public void createTemporaryReservationConcertNotFound() {
        //GIVEN
        String concertId = "test_concertId";
        //WHEN
       CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> temporaryReservationFlowFacade.createTemporaryReservation(
               userId,
               concertId,
               "",
               ""
       ));
       //THEN
        assertEquals(ErrorType.CONCERT_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @Order(2)
    @DisplayName("콘서트시리즈가 없을 경우 에러")
    public void createTemporaryReservationSeriesNotFound() {
        //GIVEN
        String concertId = concertService.create(userId, "test_title");
        String seriesId = "test_seriesId";
        //WHEN
       CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> temporaryReservationFlowFacade.createTemporaryReservation(
               userId,
               concertId,
               seriesId,
               ""
       ));
       //THEN
        assertEquals(ErrorType.CONCERT_SERIES_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @Order(3)
    @DisplayName("콘서트 시리즈 신청 기간이 지난경우")
    public void createTemporaryReservationSeatNotFound() {
        //GIVEN
        String concertId = concertService.create(userId, "test_title");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        Long now = calendar.getTimeInMillis();
        String seriesId = concertSeriesService.create(concertId, now, now, now, now);
        //WHEN
       CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> temporaryReservationFlowFacade.createTemporaryReservation(
               userId,
               concertId,
               seriesId,
               ""
       ));
       //THEN
        assertEquals(ErrorType.BOOKING_NOT_AVAILABLE, exception.getErrorType());
    }

    @Test
    @DisplayName("5분 마다 구매 안한 임시예약 삭제 테스트")
    public void timeLimitTemporaryReservation()  {
        //GIVEN
        String seriesId = "test_seriesId";
        ConcertSeat seat = ConcertSeat.newInstance(seriesId, 0, 0, 0, 10000);
        seat.reserve();
        concertSeatRepository.save(seat);
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId("test_user_id")
                .concertId("test_concert_id")
                .seriesId(seriesId)
                .title("test_title")
                .seatId(seat.getSeatId())
                .deleteAt(System.currentTimeMillis())
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        temporaryReservationFlowFacade.timeLimitTemporaryReservation();
        //THEN
        ConcertSeat nowSeat = concertSeatRepository.findById(seat.getSeatId());
        assertFalse(nowSeat.isReserved());
    }
}
