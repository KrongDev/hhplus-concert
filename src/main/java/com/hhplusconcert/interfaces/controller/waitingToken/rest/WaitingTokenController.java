package com.hhplusconcert.interfaces.controller.waitingToken.rest;

import com.hhplusconcert.application.waitingToken.facade.WaitingTokenFlowFacade;
import com.hhplusconcert.application.waitingToken.facade.WaitingTokenSeekFacade;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.interfaces.controller.waitingToken.dto.WaitingTokenCreationRequest;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/waiting-token")
@RequiredArgsConstructor
public class WaitingTokenController {
    //
    private final WaitingTokenSeekFacade waitingTokenSeekFacade;
    private final WaitingTokenFlowFacade waitingTokenFlowFacade;


    @PostMapping
    @Description("콘서트 예약 대기열 토큰 발급")
    public ResponseEntity<String> genToken(@RequestBody WaitingTokenCreationRequest request) {
        //
        request.validate();
        String userId = request.userId();
        String seriesId = request.seriesId();
        return ResponseEntity.ok(this.waitingTokenFlowFacade.createWaitingToken(userId, seriesId));
    }

    @GetMapping("/{tokenId}")
    @Description("토큰 정보 조회")
    public ResponseEntity<WaitingToken> genToken(@PathVariable String tokenId) {
        //
        return ResponseEntity.ok(this.waitingTokenSeekFacade.loadWaitingToken(tokenId));
    }

    @PatchMapping("/{tokenId}")
    @Description("토큰 정보 조회")
    public void healthCheck(@PathVariable String tokenId) {
        //
        this.waitingTokenFlowFacade.healthCheck(tokenId);
    }
}
