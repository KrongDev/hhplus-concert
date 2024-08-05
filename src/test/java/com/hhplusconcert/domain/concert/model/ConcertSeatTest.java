package com.hhplusconcert.domain.concert.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcertSeatTest {

    @Test
    @DisplayName("콘서트 좌성이 이미 판매된 경우-SEAT_ALREADY_RESERVED 에러 발생")
    public void SeatIsReserved() {
        // GIVEN
        ConcertSeat seat = ConcertSeat.builder()
                .seatId("test_seatId")
                .seriesId("test_seriesId")
                .seatRow(0)
                .seatCol(0)
                .entityVersion(0)
                .seatIndex(0)
                .price(10000)
                .reserved(true)
                .build();
        // WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, seat::reserve);
        // THEN
        assertEquals(ErrorType.SEAT_ALREADY_RESERVED, exception.getErrorType());
    }
}
