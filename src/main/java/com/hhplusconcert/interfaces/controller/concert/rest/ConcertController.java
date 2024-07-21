package com.hhplusconcert.interfaces.controller.concert.rest;

import com.hhplusconcert.application.concert.dto.ConcertDetail;
import com.hhplusconcert.application.concert.dto.ConcertSchedule;
import com.hhplusconcert.application.concert.facade.ConcertFlowFacade;
import com.hhplusconcert.application.concert.facade.ConcertSeekFacade;
import com.hhplusconcert.common.annotation.QueueCheckAnnotation;
import com.hhplusconcert.domain.concert.model.Concert;
import com.hhplusconcert.interfaces.controller.concert.dto.ConcertCreationRequest;
import com.hhplusconcert.interfaces.controller.concert.dto.ConcertSeriesCreationRequest;
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
    public ResponseEntity<String> createConcert(@RequestBody ConcertCreationRequest request) {
        //
        request.validate();
        String userId = request.userId();
        String title = request.title();
        return ResponseEntity.ok(this.concertFlowFacade.createConcert(userId, title));
    }

    @PostMapping("/series")
    @Description("콘서트 시리즈 생성")
    public ResponseEntity<String> createSeries(@RequestBody ConcertSeriesCreationRequest request) {
        //
        request.validate();
        String concertId = request.concertId();
        Long startAt = request.startAt();
        Long endAt = request.endAt();
        Long reserveStartAt = request.reserveStartAt();
        Long reserveEndAt = request.reserveEndAt();
        int maxRow = request.maxRow();
        int maxSeat = request.maxSeat();
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
