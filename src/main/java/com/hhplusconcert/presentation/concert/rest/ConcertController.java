package com.hhplusconcert.presentation.concert.rest;

import com.hhplusconcert.application.concert.dto.ConcertInfo;
import com.hhplusconcert.application.concert.dto.ConcertSeriesInfo;
import com.hhplusconcert.application.concert.dto.ConcertSheetInfo;
import com.hhplusconcert.presentation.concert.command.CreateConcertCommand;
import com.hhplusconcert.presentation.concert.command.CreateConcertSeriesCommand;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/concert")
public class ConcertController {
    //
    @PostMapping
    @Description("콘서트 생성")
    public ResponseEntity<String> createConcert(@RequestBody CreateConcertCommand command) {
        //
        command.validation();
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @PostMapping("/series")
    @Description("콘서트 시리즈 생성")
    public ResponseEntity<String> createSeries(@RequestBody CreateConcertSeriesCommand command) {
        //
        command.validation();
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @GetMapping
    @Description("콘서트 목록 조회")
    public ResponseEntity<List<ConcertInfo>> loadConcerts() {
        //
        return ResponseEntity.ok(List.of(ConcertInfo.sample()));
    }

    @GetMapping("/series/{concertId}")
    @Description("예약가능한 콘서트 날짜 조회")
    public ResponseEntity<List<ConcertSeriesInfo>> loadConcertSeries(@PathVariable String concertId) {
        //
        return ResponseEntity.ok(List.of(ConcertSeriesInfo.sample()));
    }

    @GetMapping("/sheet/{seriesId}")
    @Description("콘서트 자리 조회")
    public ResponseEntity<List<ConcertSheetInfo>> loadConcertSheets(@PathVariable String seriesId) {
        //
        return ResponseEntity.ok(List.of(ConcertSheetInfo.sample()));
    }
}
