package com.hhplusconcert.interfaces.controller.reservation.rest;

import com.hhplusconcert.application.reservation.facade.ReservationSeekFacade;
import com.hhplusconcert.domain.reservation.model.Reservation;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    //
    private final ReservationSeekFacade reservationSeekFacade;

    @GetMapping("/{reservationId}")
    @Description("예약 티켓 조회")
    public ResponseEntity<Reservation> loadReservation(@PathVariable String reservationId) {
        //
        return ResponseEntity.ok(this.reservationSeekFacade.loadReservation(reservationId));
    }

    @GetMapping()
    @Description("예약한 티켓들 조회")
    public ResponseEntity<List<Reservation>> loadReservations(@RequestParam String userId) {
        //
        return ResponseEntity.ok(this.reservationSeekFacade.loadReservationsByUserId(userId));
    }
}
