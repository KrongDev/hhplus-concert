package com.hhplusconcert.presentation.concertQueue.rest;

import com.hhplusconcert.application.concertQueue.dto.ConcertQueueInfo;
import com.hhplusconcert.presentation.concertQueue.command.JoinQueueCommand;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/concert-queue")
public class ConcertQueueController {
    /*
     */
    @PostMapping
    @Description("대기열 진입 요청")
    public ResponseEntity<String> joinQueue(@RequestBody JoinQueueCommand command) {
        //
        command.validation();
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @GetMapping
    @Description("대기열 조회")
    public ResponseEntity<ConcertQueueInfo> loadConcertQueueInfo(@RequestHeader String tokenId){
        //
        return ResponseEntity.ok(ConcertQueueInfo.sample());
    }
}
