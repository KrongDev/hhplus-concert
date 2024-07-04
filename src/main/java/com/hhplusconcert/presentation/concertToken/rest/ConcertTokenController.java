package com.hhplusconcert.presentation.concertToken.rest;

import com.hhplusconcert.application.concertToken.dto.ConcertTokenInfo;
import com.hhplusconcert.presentation.concertToken.command.GenTokenCommand;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/token")
public class ConcertTokenController {

    @PostMapping
    @Description("콘서트 예약 대기열 토큰 발급")
    public ResponseEntity<String> genToken(@RequestBody GenTokenCommand command) {
        //
        command.validation();
        return ResponseEntity.ok("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbklkIjoidGVzdF90b2tlbl9pZCIsInVzZXJJZCI6InRlc3RfdXNlcl9pZCIsIm5hbWUiOiJHZW9uIExlZSIsImNvbmNlcnRJZCI6InRlc3RfY29uY2VydCIsInNlcmllc0lkIjoidGVzdF9zZXJpZXMifQ.ap7gqrgtR_QjbvunZr5YSILzr8isxOzPfwEX_IOW08M");
    }

    @GetMapping("/{tokenId}")
    @Description("토큰 정보 조회")
    public ResponseEntity<ConcertTokenInfo> genToken(@PathVariable String tokenId) {
        //
        return ResponseEntity.ok(ConcertTokenInfo.sample());
    }

    @PatchMapping("/{tokenId}")
    @Description("토큰 healthCheck")
    public void healthCheck(@PathVariable String tokenId) {
        // Health Check
    }
}
