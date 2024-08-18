package com.hhplusconcert.domain.payment.stream;

import com.hhplusconcert.domain.outbox.service.OutboxService;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentEventRecordListener {
    //
    private final OutboxService outboxService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordMessageHandler(PaymentConfirmed event) {
        //
        this.outboxService.create(PaymentConfirmed.topicId, event);
    }
}
