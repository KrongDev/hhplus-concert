package com.hhplusconcert.application.payment.facade;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaymentFlowFacadeTest {
    @Autowired
    private PaymentFlowFacade paymentFlowFacade;
    @Autowired
    private TemporaryReservationRepository temporaryReservationRepository;


    @Test
    @DisplayName("결제시 이미 결제된 경우")
    public void processTemporaryReservationPaymentPaid() {
        //GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId("test_user_id")
                .concertId("test_concert_id")
                .seriesId("test_series_id")
                .title("test_title")
                .seatId("test_seat_id")
                .paid(true)
                .deleteAt(System.currentTimeMillis())
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentFlowFacade.processTemporaryReservationPayment(
                temporaryReservation.getTemporaryReservationId(),
                "test_user_id"
        ));
        //THEN
        assertEquals(ErrorType.TEMPORARY_RESERVATION_ALREADY_PURCHASED, exception.getErrorType());
    }

    @Test
    @DisplayName("결제시 시간이 지나 예약이 삭제된 경우")
    public void processTemporaryReservationPaymentDeleted() {
        //GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId("test_user_id")
                .concertId("test_concert_id")
                .seriesId("test_series_id")
                .title("test_title")
                .seatId("test_seat_id")
                .deleteAt(System.currentTimeMillis())
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentFlowFacade.processTemporaryReservationPayment(
                temporaryReservation.getTemporaryReservationId(),
                "test_user_id"
        ));
        //THEN
        assertEquals(ErrorType.PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION, exception.getErrorType());
    }

    @Test
    @DisplayName("결제시 포인트가 요청 금액보다 적을 경우")
    public void processTemporaryReservationPaymentNotPoint() {
        //GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .temporaryReservationId(UUID.randomUUID().toString())
                .userId("test_user_id")
                .concertId("test_concert_id")
                .seriesId("test_series_id")
                .title("test_title")
                .seatId("test_seat_id")
                .deleteAt(System.currentTimeMillis() + 1000000)
                .price(10000)
                .build();
        temporaryReservationRepository.save(temporaryReservation);
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentFlowFacade.processTemporaryReservationPayment(
                temporaryReservation.getTemporaryReservationId(),
                "test_user_id"
        ));
        //THEN
        assertEquals(ErrorType.INSUFFICIENT_POINT, exception.getErrorType());
    }
}
