package com.hhplusconcert.application.point.facade;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import com.hhplusconcert.domain.point.service.PointService;
import com.hhplusconcert.infra.point.orm.PointHistoryJpaRepository;
import com.hhplusconcert.infra.point.orm.PointJpaRepository;
import com.hhplusconcert.infra.point.orm.jpo.PointJpo;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class PointFlowFacadeTest {
    private static final Logger log = LoggerFactory.getLogger(PointFlowFacadeTest.class);
    @Autowired
    private PointFlowFacade pointFlowFacade;
    @Autowired
    private PointService pointService;
    @Autowired
    private PointHistoryService pointHistoryService;
    @Autowired
    private PointJpaRepository pointJpaRepository;
    @Autowired
    private PointHistoryJpaRepository pointHistoryJpaRepository;

    private final String userId = "test_userId";

    @BeforeEach
    public void setUp() {
        //
        pointJpaRepository.deleteById(userId);
        pointHistoryJpaRepository.deleteAll();
    }

    @AfterEach
    public void after() {
        pointJpaRepository.deleteById(userId);
        pointHistoryJpaRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("포인트 충전시 충전금액이 부적절한 경우-INVALID_POINT 에러 발생")
    void chargePoint() {
        //GIVEN
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointFlowFacade.chargePoint(userId, -1000));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @Order(2)
    @DisplayName("포인트 사용시 사용금액이 부적절한 경우-INVALID_POINT 에러 발생")
    void usePointInvalidPoint() {
        //GIVEN
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointFlowFacade.usePoint(userId, -1000, ""));
        //THEN
        assertEquals(ErrorType.INVALID_POINT, exception.getErrorType());
    }

    @Test
    @Order(3)
    @DisplayName("포인트 사용시 잔여 포인트가 부족한 경우-INSUFFICIENT_POINT 에러 발생")
    void usePointInsufficientPoint() {
        //GIVEN
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> pointFlowFacade.usePoint(userId, 1000, ""));
        //THEN
        assertEquals(ErrorType.INSUFFICIENT_POINT, exception.getErrorType());
    }

    @Test
    @Order(4)
    @DisplayName("포인트 충전 동시성 테스트 - 낙관적 락")
    void chargePointOptimisticLock() throws InterruptedException {
        //GIVEN
        int memberCount = 2;
        int chargePoint = 10000;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch doneLatch = new CountDownLatch(memberCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < memberCount; i++) {
            executorsService.submit(() -> {
                try {
                    this.pointFlowFacade.chargePoint(userId, chargePoint);
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
        Point point = pointService.loadPoint(userId);


        // 로그에 결과 출력
        log.info("Expected Point: " + chargePoint + ", Actual Point: " + point.getPoint());
        log.info("Total time taken: " + totalTime + " milliseconds");
        assertEquals(chargePoint, point.getPoint());
        Thread.sleep(2000);
        List<PointHistory> pointHistories = this.pointHistoryService.loadPointHistories(userId);
        assertEquals(1, pointHistories.size());
        assertEquals(chargePoint, pointHistories.get(0).getAmount());
        assertEquals(PointHistoryStatus.CHARGE, pointHistories.get(0).getStatus());
    }

    @Test
    @Order(5)
    @DisplayName("포인트 사용 동시성 테스트 - 낙관적 락")
    void usePointOptimisticLock() throws InterruptedException {
        //GIVEN
        int chargePoint = 100000;
        int usePoint = 10000;
        this.pointJpaRepository.save(new PointJpo(Point.newInstance(userId, chargePoint)));
        //WHEN
        int memberCount = 2;
        ExecutorService executorsService = Executors.newFixedThreadPool(memberCount);
        CountDownLatch doneLatch = new CountDownLatch(memberCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < memberCount; i++) {
            executorsService.submit(() -> {
                try {
                    this.pointFlowFacade.usePoint(userId, usePoint, "");
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
        int expectedPoint = chargePoint - usePoint;
        Point point = pointService.loadPoint(userId);

        // 로그에 결과 출력
        log.info("Expected Point: " + expectedPoint + ", Actual Point: " + point.getPoint());
        log.info("Total time taken: " + totalTime + " milliseconds");
        assertEquals(expectedPoint, point.getPoint());
        Thread.sleep(2000);
        List<PointHistory> pointHistories = this.pointHistoryService.loadPointHistories(userId);
        assertEquals(1, pointHistories.size());
        assertEquals(usePoint, pointHistories.get(0).getAmount());
        assertEquals(PointHistoryStatus.USE, pointHistories.get(0).getStatus());
    }
}
