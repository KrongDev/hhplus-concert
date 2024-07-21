package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.repository.ConcertSeriesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertSeriesServiceTest {

    @InjectMocks
    private ConcertSeriesService concertSeriesService;

    @Mock
    private ConcertSeriesRepository concertSeriesRepository;

    @Test
    @DisplayName("조회시 예약 불가능한 날짜인경우")
    public void loadConcertSeriesReservationAvailable() {
        //GIVEN
        String seriesId = "test_series_id";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        Long start = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, -1);
        Long end = calendar.getTimeInMillis();
        when(concertSeriesRepository.findById(seriesId)).thenReturn(ConcertSeries.builder().reserveStartAt(start).reserveEndAt(end).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> concertSeriesService.loadConcertSeriesReservationAvailable(seriesId));
        //THEN
        assertEquals(ErrorType.BOOKING_NOT_AVAILABLE, exception.getErrorType());
    }
}
