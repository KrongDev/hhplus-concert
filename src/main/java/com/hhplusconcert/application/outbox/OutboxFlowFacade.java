package com.hhplusconcert.application.outbox;

import com.hhplusconcert.domain.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxFlowFacade {
    //
    private final OutboxService outboxService;

    public void successPublish(String eventId) {
        //
        this.outboxService.successPublish(eventId);
    }
}
