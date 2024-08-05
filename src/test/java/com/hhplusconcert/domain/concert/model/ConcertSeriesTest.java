package com.hhplusconcert.domain.concert.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcertSeriesTest {

    @Test
    @DisplayName("예약 불가능한 콘서트 시리즈인 경우-BOOKING_NOT_AVAILABLE 에러 발생")
    public void validateReservationAvailable() {
        // GIVEN
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, -1);
        long end = calendar.getTimeInMillis();
        ConcertSeries series = ConcertSeries.builder()
                .reserveStartAt(start)
                .reserveEndAt(end)
                .build();
        // WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, series::validateReservationAvailable);
        // THEN
        assertEquals(ErrorType.BOOKING_NOT_AVAILABLE, exception.getErrorType());
    }
}
