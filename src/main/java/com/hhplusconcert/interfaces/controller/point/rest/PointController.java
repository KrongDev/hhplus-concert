package com.hhplusconcert.interfaces.controller.point.rest;

import com.hhplusconcert.application.point.facade.PointFlowFacade;
import com.hhplusconcert.application.point.facade.PointSeekFacade;
import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.interfaces.controller.point.command.ChargePointCommand;
import com.hhplusconcert.interfaces.controller.point.command.UsePointCommand;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointFlowFacade pointFlowFacade;
    private final PointSeekFacade pointSeekFacade;

    @GetMapping("/{userId}")
    @Description("포인트 조회")
    public ResponseEntity<Point> loadPoint(@PathVariable String userId){
        //
        return ResponseEntity.ok(this.pointSeekFacade.loadPoint(userId));
    }

    @GetMapping("/history/{userId}")
    @Description("포인트 사용 내역 조회")
    public ResponseEntity<List<PointHistory>> loadPointHistories(@PathVariable String userId){
        //
        return ResponseEntity.ok(this.pointSeekFacade.loadPointHistoryByPointId(userId));
    }

    @PatchMapping("/charge")
    @Description("포인트 충전")
    public void chargePoint (@RequestBody ChargePointCommand command) {
        //
        command.validate();
        String userId = command.userId();
        int amount = command.amount();
        this.pointFlowFacade.chargePoint(userId, amount);
    }

    @PatchMapping("/use")
    @Description("포인트 사용")
    public void usePoint (@RequestBody UsePointCommand command) {
        //
        command.validate();
        String userId = command.userId();
        String paymentId = command.paymentId();
        int amount = command.amount();
        this.pointFlowFacade.usePoint(userId, amount, paymentId);
    }
}
