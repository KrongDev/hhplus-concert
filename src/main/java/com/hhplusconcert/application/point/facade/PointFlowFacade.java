package com.hhplusconcert.application.point.facade;

import com.hhplusconcert.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PointFlowFacade {

    private final PointService pointService;

//    @RedissonLock("chargePoint")
    @Transactional
    public void chargePoint(String userId, int amount) {
        //
        this.pointService.charge(userId, amount);
    }

    @Transactional
    public void usePoint(
            String userId,
            int amount,
            String paymentId
    ) {
        //
        this.pointService.use(userId, paymentId, amount);
    }
}
