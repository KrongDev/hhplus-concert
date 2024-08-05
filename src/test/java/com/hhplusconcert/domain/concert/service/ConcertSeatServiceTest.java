package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertSeatServiceTest {

    @InjectMocks
    private ConcertSeatService concertSeatService;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @Test
    @DisplayName("콘서트 좌석 생성 검증")
    public void create() {
        //GIVEN
        String seriesId = "test_seriesId";
        int maxRow = 5;
        int maxSeat = 50;
        //WHEN
        List<ConcertSeat> concertSeatList = concertSeatService.genSeatWithSeries(seriesId, maxRow, maxSeat);
        //THEN
        int size = concertSeatList.size();
        assertEquals(maxSeat, size);
        assertEquals(0, concertSeatList.get(0).getSeatRow());
        assertEquals(0, concertSeatList.get(0).getSeatRow());
        assertEquals(maxSeat - 1, concertSeatList.get(size - 1).getSeatIndex());
    }

    @Test
    @DisplayName("콘서트 좌석이 이미 판매된 경우-SEAT_ALREADY_RESERVED 에러 발생")
    public void reserveSeat() {
        //GIVEN
        String seriesId = "test_seriesId";
        when(concertSeatRepository.findById(seriesId)).thenReturn(ConcertSeat.builder().reserved(true).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> concertSeatService.reserveSeat(seriesId));
        //THEN
        assertEquals(ErrorType.SEAT_ALREADY_RESERVED, exception.getErrorType());
    }
}
