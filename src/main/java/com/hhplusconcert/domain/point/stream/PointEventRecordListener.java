package com.hhplusconcert.domain.point.stream;

import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.repository.OutboxRepository;
import com.hhplusconcert.domain.point.event.ChargedPoint;
import com.hhplusconcert.domain.point.event.UsedPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PointEventRecordListener {
    private final OutboxRepository outboxRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordMessageHandler(ChargedPoint event) {
        //
        this.outboxRepository.save(Outbox.from(event));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordMessageHandler(UsedPoint event) {
        //
        this.outboxRepository.save(Outbox.from(event));
    }
}
