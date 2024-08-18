package com.hhplusconcert.application.point.facade;

import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointHistoryFlowFacade {
    //
    private final PointHistoryService pointHistoryService;

    public void createPointChargedHistory(
            String requestUserId,
            int amount
    ) {
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.CHARGE);
    }

    public void createPointUsedHistory(
            String requestUserId,
            int amount,
            String paymentId
    ) {
        //
        this.pointHistoryService.createPointHistory(requestUserId, amount, PointHistoryStatus.USE, paymentId);
    }
}
