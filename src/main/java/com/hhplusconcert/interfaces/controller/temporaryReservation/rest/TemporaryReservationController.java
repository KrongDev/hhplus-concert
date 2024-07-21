package com.hhplusconcert.interfaces.controller.temporaryReservation.rest;

import com.hhplusconcert.application.temporaryReservation.dto.TemporaryReservationInfo;
import com.hhplusconcert.application.temporaryReservation.facade.TemporaryReservationFlowFacade;
import com.hhplusconcert.application.temporaryReservation.facade.TemporaryReservationSeekFacade;
import com.hhplusconcert.common.annotation.QueueCheckAnnotation;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.interfaces.controller.temporaryReservation.dto.TemporaryReservationCreationRequest;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temporary-reservation")
@RequiredArgsConstructor
public class TemporaryReservationController {
    //
    private final TemporaryReservationFlowFacade temporaryReservationFlowFacade;
    private final TemporaryReservationSeekFacade temporaryReservationSeekFacade;

    @PostMapping()
    @QueueCheckAnnotation
    @Description("임시 콘서트 좌석 예약")
    public ResponseEntity<String> createTemporaryReservation(@RequestHeader String tokenId, @RequestBody TemporaryReservationCreationRequest request) {
        //
        request.validate();
        String userId = request.userId();
        String concertId = request.concertId();
        String seriesId = request.seriesId();
        String seatId = request.seatId();

        return ResponseEntity.ok(this.temporaryReservationFlowFacade.createTemporaryReservation(
                userId,
                concertId,
                seriesId,
                seatId
        ));
    }

    @GetMapping()
    @Description("임시 예약 목록 조회")
    public ResponseEntity<List<TemporaryReservation>> loadTemporaryReservations(@RequestParam String userId) {
        //
        return ResponseEntity.ok(this.temporaryReservationSeekFacade.loadTemporaryReservations(userId));
    }

    @GetMapping("/{temporaryReservationId}")
    @Description("임시 예약 단건 조회")
    public ResponseEntity<TemporaryReservationInfo> loadTemporaryReservation(@PathVariable String temporaryReservationId) {
        //
        return ResponseEntity.ok(this.temporaryReservationSeekFacade.loadTemporaryReservation(temporaryReservationId));
    }
}
