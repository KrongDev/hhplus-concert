package com.hhplusconcert.interfaces.controller.waitingQueue.rest;

import com.hhplusconcert.application.waitingQueue.facade.WaitingQueueFlowFacade;
import com.hhplusconcert.application.waitingQueue.facade.WaitingQueueSeekFacade;
import com.hhplusconcert.interfaces.controller.waitingQueue.dto.JoinWaitingQueueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("waiting-queue")
@RequiredArgsConstructor
public class WaitingQueueController {
    //
    private final WaitingQueueFlowFacade waitingQueueFlowFacade;
    private final WaitingQueueSeekFacade waitingQueueSeekFacade;

    @PostMapping("/join")
    public ResponseEntity<Long> joinWaitingQueue(@RequestBody JoinWaitingQueueRequest request) {
        //
        request.validate();
        String tokenId = request.tokenId();
        return ResponseEntity.ok(this.waitingQueueFlowFacade.joinQueue(tokenId));
    }

    @GetMapping("/waiting-count")
    public ResponseEntity<Long> loadNowWaitingCount(@RequestParam String tokenId) {
        //
        return ResponseEntity.ok(this.waitingQueueSeekFacade.loadNowWaitingCount(tokenId));
    }
}
