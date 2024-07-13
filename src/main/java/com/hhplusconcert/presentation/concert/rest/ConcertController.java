package com.hhplusconcert.presentation.concert.rest;

import com.hhplusconcert.application.concert.dto.ConcertDetail;
import com.hhplusconcert.application.concert.dto.ConcertSchedule;
import com.hhplusconcert.application.concert.facade.ConcertFlowFacade;
import com.hhplusconcert.application.concert.facade.ConcertSeekFacade;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.presentation.concert.command.CreateConcertCommand;
import com.hhplusconcert.presentation.concert.command.CreateConcertSeriesCommand;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {
    //
    private final ConcertFlowFacade concertFlowFacade;
    private final ConcertSeekFacade concertSeekFacade;

    @PostMapping
    @Description("콘서트 생성")
    public ResponseEntity<String> createConcert(@RequestBody CreateConcertCommand command) {
        //
        command.validation();
        String userId = command.getUserId();
        String title = command.getTitle();
        return ResponseEntity.ok(this.concertFlowFacade.createConcert(userId, title));
    }

    @PostMapping("/series")
    @Description("콘서트 시리즈 생성")
    public ResponseEntity<String> createSeries(@RequestBody CreateConcertSeriesCommand command) {
        //
        command.validation();
        String concertId = command.getConcertId();
        Long startAt = command.getStartAt();
        Long endAt = command.getEndAt();
        Long reserveStartAt = command.getReserveStartAt();
        Long reserveEndAt = command.getReserveEndAt();
        int maxRow = command.getMaxRow();
        int maxSeat = command.getMaxSeat();
        return ResponseEntity.ok(this.concertFlowFacade.createConcertSeries(
                concertId,
                startAt,
                endAt,
                reserveStartAt,
                reserveEndAt,
                maxRow,
                maxSeat
        ));
    }

    @GetMapping
    @Description("콘서트 목록 조회")
    public ResponseEntity<List<Concert>> loadConcerts() {
        //
        return ResponseEntity.ok(this.concertSeekFacade.loadConcerts());
    }

    @GetMapping("/series/{concertId}")
    @Description("예약가능한 콘서트 날짜 조회")
    public ResponseEntity<ConcertSchedule> loadConcertSeries(@PathVariable String concertId) {
        //
        return ResponseEntity.ok(this.concertSeekFacade.loadConcertSeries(concertId));
    }

    @GetMapping("/seat/{seriesId}")
    @Description("콘서트 자리 조회")
    public ResponseEntity<ConcertDetail> loadConcertSheets(@PathVariable String seriesId) {
        //
        return ResponseEntity.ok(this.concertSeekFacade.loadConcertSeats(seriesId));
    }
}
