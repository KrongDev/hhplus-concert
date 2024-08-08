package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointHistoryHandler {
    //
    private final PointHistoryService pointHistoryService;

    @EventListener
    public void on(ChargedPoint event) {
        //
        String requestUserId = event.requestUserId();
        int amount = event.amount();
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.CHARGE);
    }

    @EventListener
    public void on(UsedPoint event) {
        //
        String requestUserId = event.requestUserId();
        String paymentId = event.paymentId();
        int amount = event.amount();
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.USE, paymentId);
    }
}
