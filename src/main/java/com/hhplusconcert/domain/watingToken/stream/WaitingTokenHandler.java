package com.hhplusconcert.domain.watingToken.stream;

import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WaitingTokenHandler {
    //
    private final WaitingTokenService waitingTokenService;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(PaymentConfirmed event) {
        String userId = event.userId();
        String seriesId = event.seriesId();
        this.waitingTokenService.deleteByUserIdAndSeriesId(userId, seriesId);
    }
}
