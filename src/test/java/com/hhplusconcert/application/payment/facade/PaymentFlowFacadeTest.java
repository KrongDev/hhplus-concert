package com.hhplusconcert.application.payment.facade;

import com.hhplusconcert.common.TruncateTableComponent;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.service.PointService;
import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.service.ReservationService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class PaymentFlowFacadeTest {
    private static final Logger log = LoggerFactory.getLogger(PaymentFlowFacadeTest.class);
    @Autowired
    private PaymentFlowFacade paymentFlowFacade;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TemporaryReservationRepository temporaryReservationRepository;
    @Autowired
    private PointService pointService;
    @Autowired
    private TruncateTableComponent truncateTableComponent;

    private final String userId = "test_user_id";
    private final String concertId = "test_concert_id";
    private final String seriesId = "test_series_id";
    private final String title = "test_title";
    private final String seatId = "test_seat_id";

    @BeforeEach
    public void setUp() {
        truncateTableComponent.truncateTable(() -> {}, "temporary_reservation", "reservation", "payment", "point");
    }

    @Test
    @DisplayName("결제시 이미 결제된 경우-TEMPORARY_RESERVATION_ALREADY_PURCHASED 에러 발생")
    public void processTemporaryReservationPaymentPaid() {
        //GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId(userId)
                .concertId(concertId)
                .seriesId(seriesId)
                .title(title)
                .seatId(seatId)
                .paid(true)
                .deleteAt(System.currentTimeMillis())
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentFlowFacade.processTemporaryReservationPayment(
                temporaryReservation.getTemporaryReservationId(),
                userId
        ));
        //THEN
        assertEquals(ErrorType.TEMPORARY_RESERVATION_ALREADY_PURCHASED, exception.getErrorType());
    }

    @Test
    @DisplayName("결제시 시간이 지나 예약이 삭제된 경우-PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION 에러 발생")
    public void processTemporaryReservationPaymentDeleted() {
        //GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId(userId)
                .concertId(concertId)
                .seriesId(seriesId)
                .title(title)
                .seatId(seatId)
                .deleteAt(System.currentTimeMillis())
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentFlowFacade.processTemporaryReservationPayment(
                temporaryReservation.getTemporaryReservationId(),
                userId
        ));
        //THEN
        assertEquals(ErrorType.PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION, exception.getErrorType());
    }

    @Test
    @DisplayName("결제시 포인트가 요청 금액보다 적을 경우-INSUFFICIENT_POINT 에러 발생")
    public void processTemporaryReservationPaymentNotPoint() {
        //GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId(userId)
                .concertId(concertId)
                .seriesId(seriesId)
                .title(title)
                .seatId(seatId)
                .deleteAt(System.currentTimeMillis() + 1000000)
                .price(10000)
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentFlowFacade.processTemporaryReservationPayment(
                temporaryReservation.getTemporaryReservationId(),
                userId
        ));
        //THEN
        assertEquals(ErrorType.INSUFFICIENT_POINT, exception.getErrorType());
    }

    @Test
    @DisplayName("임시예약 좌석 결제 동시성 테스트 - 낙관적 락")
    void temporaryReservationOptimisticLock() throws InterruptedException {
        //GIVEN
        int userPoint = 100000;
        int price = 10000;
        TemporaryReservation temporaryReservation = TemporaryReservation
                .newInstance(
                        userId,
                        concertId,
                        title,
                        seriesId,
                        seatId,
                        0,
                        0,
                        price
                );
        this.temporaryReservationRepository.save(temporaryReservation);
        this.pointService.charge(userId, userPoint);
        //WHEN
        int memberCount = 10;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch doneLatch = new CountDownLatch(memberCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < memberCount; i++) {
            executorsService.submit(() -> {
                try {
                    this.paymentFlowFacade.processTemporaryReservationPayment(temporaryReservation.getTemporaryReservationId(), userId);
                }catch (ObjectOptimisticLockingFailureException e) {
                    log.warn("버전 충돌로 인한 Process 종료");
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    doneLatch.countDown(); // 작업 완료를 카운트
                }
            });
        }

        // 모든 작업이 완료되기를 대기
        doneLatch.await();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        Thread.sleep(1000);
        //then
        List<Reservation> reservations = this.reservationService.loadAllReservationsByUserId(userId);
        Point point = this.pointService.loadPoint(userId);
        // 로그에 결과 출력
        log.info("Expected Reservation Size: 1, Actual Reservation Size: " + reservations.size());
        log.info("Expected Point: "+ (userPoint - price) +", Actual Point: " + point.getPoint());
        log.info("Total time taken: " + totalTime + " milliseconds");
        assertEquals(1, reservations.size());
        assertEquals(userPoint - price, point.getPoint());
        Reservation reservation = reservations.get(0);
        assertEquals(temporaryReservation.getConcertId(), reservation.getConcertId());
        assertEquals(temporaryReservation.getSeriesId(), reservation.getSeriesId());
        assertEquals(temporaryReservation.getTitle(), reservation.getTitle());
        assertEquals(temporaryReservation.getSeatId(), reservation.getSeatId());
        assertEquals(temporaryReservation.getSeatRow(), reservation.getSeatRow());
        assertEquals(temporaryReservation.getSeatCol(), reservation.getSeatCol());
    }
}
