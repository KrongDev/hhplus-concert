package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.domain.concert.service.ConcertSeatService;
import com.hhplusconcert.domain.concert.service.ConcertSeriesService;
import com.hhplusconcert.domain.concert.service.ConcertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConcertSeekFacadeTest {
    @Autowired
    private ConcertService concertService;
    @Autowired
    private ConcertSeriesService concertSeriesService;
    @Autowired
    private ConcertSeatService concertSeatService;
}
