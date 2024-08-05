package com.hhplusconcert.domain.temporaryReservation.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemporaryReservationTest {

    @Test
    @DisplayName("이미 결제된 경우-TEMPORARY_RESERVATION_ALREADY_PURCHASED 에러 발생")
    public void finalizeConcertReservationWithPaid() {
        // GIVEN
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .paid(true)
                .build();
        // WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, temporaryReservation::finalizeConcertReservation);
        //THEN
        assertEquals(ErrorType.TEMPORARY_RESERVATION_ALREADY_PURCHASED, exception.getErrorType());
    }

    @Test
    @DisplayName("임시예약이 n분 지나 삭제처리 혹은 예정 된 경우-PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION 에러 발생")
    public void finalizeConcertReservationWithDeleted() {
        // GIVEN
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -3);
        TemporaryReservation temporaryReservation = TemporaryReservation.builder()
                .deleteAt(calendar.getTimeInMillis())
                .build();
        // WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, temporaryReservation::finalizeConcertReservation);
        //THEN
        assertEquals(ErrorType.PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION, exception.getErrorType());
    }
}
