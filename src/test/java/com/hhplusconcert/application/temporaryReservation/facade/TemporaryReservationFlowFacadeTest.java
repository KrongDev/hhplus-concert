package com.hhplusconcert.application.temporaryReservation.facade;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.TemporaryReservationJpaRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.jpo.TemporaryReservationJpo;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TemporaryReservationFlowFacadeTest {
    private static final Logger log = LoggerFactory.getLogger(TemporaryReservationFlowFacadeTest.class);
    @Autowired
    private TemporaryReservationFlowFacade temporaryReservationFlowFacade;
    @Autowired
    private ConcertService concertService;
    @Autowired
    private ConcertSeriesService concertSeriesService;
    @Autowired
    private TemporaryReservationRepository temporaryReservationRepository;
    @Autowired
    private TemporaryReservationJpaRepository temporaryReservationJpaRepository;
    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    private final String userId = "test_userId";

    @Test
    @Order(1)
    @DisplayName("콘서트가 없을 경우-CONCERT_NOT_FOUND 에러 발생")
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
    @DisplayName("콘서트시리즈가 없을 경우-CONCERT_SERIES_NOT_FOUND 에러 발생")
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
    @DisplayName("콘서트 시리즈 신청 기간이 지난경우-BOOKING_NOT_AVAILABLE 에러 발생")
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

    @Test
    @DisplayName("좌석 임시예약 동시성 테스트 - 낙관적 락")
    void temporaryReservationOptimisticLock() throws InterruptedException {
        //GIVEN
        String concertId = this.concertService.create(userId, "lock_concert");
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.MINUTE, 10);
        Long end = calendar.getTimeInMillis();
        String seriesId = this.concertSeriesService.create(concertId, now, end, now, end);
        ConcertSeat seat = ConcertSeat.newInstance(seriesId, 0, 0, 0, 10000);
        this.concertSeatRepository.save(seat);
        this.temporaryReservationJpaRepository.deleteAll();
        //WHEN
        int memberCount = 1000;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch doneLatch = new CountDownLatch(memberCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < memberCount; i++) {
            int finalI = i;
            executorsService.submit(() -> {
                try {
                    this.temporaryReservationFlowFacade.createTemporaryReservation(userId + finalI, concertId, seriesId, seat.getSeatId());
                } catch (ObjectOptimisticLockingFailureException e) {
                    log.warn("버전 충돌로 인한 Process 종료");
                } finally {
                    doneLatch.countDown(); // 작업 완료를 카운트
                }
            });
        }

        // 모든 작업이 완료되기를 대기
        doneLatch.await();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        //then
        ConcertSeat resSeat = this.concertSeatRepository.findById(seat.getSeatId());
        List<TemporaryReservationJpo> jpos = this.temporaryReservationJpaRepository.findAll();
        TemporaryReservationJpo temporaryReservation = jpos.get(0);
        // 로그에 결과 출력
        log.info("Expected Seat Reserved: true, Actual Seat Reserved: " + resSeat.isReserved() + ", UserId: " + temporaryReservation.getUserId());
        log.info("Total time taken: " + totalTime + " milliseconds");
        assertEquals(1, jpos.size());
        assertEquals(concertId, temporaryReservation.getConcertId());
        assertEquals(seriesId, temporaryReservation.getSeriesId());
        assertEquals(seat.getSeatId(), temporaryReservation.getSeatId());
        assertEquals(seat.getSeatRow(), temporaryReservation.getSeatRow());
        assertEquals(seat.getSeatCol(), temporaryReservation.getSeatCol());
    }
}
