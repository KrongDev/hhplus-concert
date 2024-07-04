package com.hhplusconcert.presentation.temporaryReservation.rest;

import com.hhplusconcert.application.temporaryReservation.dto.TemporaryReservation;
import com.hhplusconcert.presentation.temporaryReservation.command.CreateTemporaryReservationCommand;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/temporary-reservation")
public class TemporaryReservationController {

    @PostMapping()
    @Description("임시 콘서트 좌석 예약")
    public ResponseEntity<String> createTemporaryReservation(@RequestBody CreateTemporaryReservationCommand command) {
        //
        command.validation();
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @GetMapping("/{temporaryReservationId}")
    @Description("임시 예약 단건 조회")
    public ResponseEntity<TemporaryReservation> createTemporaryReservation(@PathVariable String temporaryReservationId) {
        //
        return ResponseEntity.ok(TemporaryReservation.sample());
    }
}
