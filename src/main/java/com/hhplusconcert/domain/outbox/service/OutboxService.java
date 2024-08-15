package com.hhplusconcert.domain.outbox.service;

import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {
    //
    private final OutboxRepository outboxRepository;

    @Transactional
    public void published(String id) {
        Outbox outbox = loadOutbox(id);
        outbox.successPublish();
        this.outboxRepository.save(outbox);
    }

    public Outbox loadOutbox(String id) {
        //
        return this.outboxRepository.findOutbox(id);
    }

    public List<Outbox> loadUnPublishedEvents() {
        //
        return this.outboxRepository.findUnPublishedEvents();
    }
}
