package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConcertFlowFacadeTest {

    @Autowired
    private ConcertService concertService;
    @Autowired
    private ConcertFlowFacade concertFlowFacade;
    @Autowired
    private ConcertSeriesService concertSeriesService;
    @Autowired
    private ConcertSeatService concertSeatService;


    private String concertId;

    @Test
    @Order(1)
    @DisplayName("콘서트 생성 테스트")
    void createConcert() {
        //GIVEN
        String userId = "test_user";
        String title = "test_title";
        //WHEN
        concertId = concertService.create(userId, title);

        //THEN
        Concert concert = concertService.loadConcert(concertId);
        assertNotNull(concert);
        assertEquals(userId, concert.getCreator());
        assertEquals(title, concert.getTitle());
    }

    @Test
    @Order(2)
    @DisplayName("콘서트 날짜 생성 및 시트 생성")
    void createConcertSeries() {
        //GIVEN
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        Long endTime = calendar.getTimeInMillis();
        Long startAt = now;
        Long endAt = endTime;
        Long reserveStartAt = now;
        Long reserveEndAt = endTime;
        int maxRow = 5;
        int maxSeat = 50;
        //WHEN
        String seriesId = concertFlowFacade.createConcertSeries(
                concertId,
                startAt,
                endAt,
                reserveStartAt,
                reserveEndAt,
                maxRow,
                maxSeat
        );
        //THEN
        ConcertSeries concertSeries = concertSeriesService.loadConcertSeries(seriesId);
        List<ConcertSeat> concertSeats = concertSeatService.loadConcertSeatsBySeries(seriesId);

        assertNotNull(concertSeries);
        assertEquals(concertId, concertSeries.getConcertId());
        assertEquals(startAt, concertSeries.getStartAt());
        assertEquals(endAt, concertSeries.getEndAt());
        assertEquals(reserveStartAt, concertSeries.getReserveStartAt());
        assertEquals(reserveEndAt, concertSeries.getReserveEndAt());
        assertNotNull(concertSeats);
        assertEquals(maxSeat, concertSeats.size());
    }
}
