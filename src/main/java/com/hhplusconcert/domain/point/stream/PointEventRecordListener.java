package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.domain.outbox.service.OutboxService;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PointEventRecordListener {
    private final OutboxService outboxService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordMessageHandler(ChargedPoint event) {
        //
        this.outboxService.create(ChargedPoint.topicId, event);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordMessageHandler(UsedPoint event) {
        //
        this.outboxService.create(UsedPoint.topicId, event);
    }
}
