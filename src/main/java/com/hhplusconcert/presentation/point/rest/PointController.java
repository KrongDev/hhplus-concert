package com.hhplusconcert.presentation.point.rest;

import com.hhplusconcert.application.point.dto.PointHistoryInfo;
import com.hhplusconcert.application.point.dto.PointInfo;
import com.hhplusconcert.presentation.point.command.ChargePointCommand;
import com.hhplusconcert.presentation.point.command.UsePointCommand;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    @GetMapping("/{userId}")
    @Description("포인트 조회")
    public ResponseEntity<PointInfo> loadPoint(@PathVariable String userId){
        //
        return ResponseEntity.ok(PointInfo.sample());
    }

    @GetMapping("/history/{userId}")
    @Description("포인트 사용 내역 조회")
    public ResponseEntity<List<PointHistoryInfo>> loadPointHistories(@PathVariable String userId){
        //
        return ResponseEntity.ok(List.of(PointHistoryInfo.sample()));
    }

    @PatchMapping("/charge")
    @Description("포인트 충전")
    public void chargePoint (@RequestBody ChargePointCommand command) {
        //
        command.validate();
    }

    @PatchMapping("/use")
    @Description("포인트 사용")
    public void usePoint (@RequestBody UsePointCommand command) {
        //
        command.validate();
    }
}
