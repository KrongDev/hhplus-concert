package com.hhplusconcert.presentation.reservation.rest;

import com.hhplusconcert.application.reservation.dto.ReservationInfo;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @GetMapping("/{reservationId}")
    @Description("예약 티켓 조회")
    public ResponseEntity<ReservationInfo> loadReservation(@PathVariable String reservationId) {
        //
        return ResponseEntity.ok(ReservationInfo.sample());
    }

    @GetMapping()
    @Description("예약한 티켓들 조회")
    public ResponseEntity<List<ReservationInfo>> loadReservations(@RequestParam String userId) {
        //
        return ResponseEntity.ok(List.of(ReservationInfo.sample()));
    }
}
