package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ConcertSeatServiceTest {

    @InjectMocks
    private ConcertSeatService concertSeatService;

    @Test
    @DisplayName("시트 생성 로직 테스트")
    void genSeatWithSeries() {
        //GIVEN
        String seriesId = "test_series_id";
        int maxRow = 5;
        int maxSeat = 50;
        //WHEN
        List<ConcertSeat> seats = concertSeatService.genSeatWithSeries(seriesId, maxRow, maxSeat);
        //THEN
        assertNotNull(seats);
        assertEquals(seats.size(), maxSeat);
    }
}
