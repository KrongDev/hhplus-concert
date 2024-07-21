package com.hhplusconcert.interfaces.controller.concert.rest;

import com.hhplusconcert.application.concert.dto.ConcertDetail;
import com.hhplusconcert.application.concert.dto.ConcertSchedule;
import com.hhplusconcert.application.concert.facade.ConcertFlowFacade;
import com.hhplusconcert.application.concert.facade.ConcertSeekFacade;
import com.hhplusconcert.common.annotation.QueueCheckAnnotation;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.interfaces.controller.concert.command.ConcertCreationCommand;
import com.hhplusconcert.interfaces.controller.concert.command.ConcertSeriesCreationCommand;
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
    public ResponseEntity<String> createConcert(@RequestBody ConcertCreationCommand command) {
        //
        command.validate();
        String userId = command.userId();
        String title = command.title();
        return ResponseEntity.ok(this.concertFlowFacade.createConcert(userId, title));
    }

    @PostMapping("/series")
    @Description("콘서트 시리즈 생성")
    public ResponseEntity<String> createSeries(@RequestBody ConcertSeriesCreationCommand command) {
        //
        command.validate();
        String concertId = command.concertId();
        Long startAt = command.startAt();
        Long endAt = command.endAt();
        Long reserveStartAt = command.reserveStartAt();
        Long reserveEndAt = command.reserveEndAt();
        int maxRow = command.maxRow();
        int maxSeat = command.maxSeat();
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
    @QueueCheckAnnotation
    @Description("콘서트 자리 조회")
    public ResponseEntity<ConcertDetail> loadConcertSheets(@RequestHeader String tokenId, @PathVariable String seriesId) {
        //
        return ResponseEntity.ok(this.concertSeekFacade.loadConcertSeats(seriesId));
    }
}
