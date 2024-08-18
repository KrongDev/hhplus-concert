package com.hhplusconcert.domain.outbox.service;

import com.hhplusconcert.domain.common.Event;
import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {
    //
    private final OutboxRepository outboxRepository;

    @Transactional
    public void create(String topicId, Event event) {
        //
        this.outboxRepository.save(Outbox.from(topicId, event));
    }

    public Outbox loadOutbox(String id) {
        //
        return this.outboxRepository.findOutbox(id);
    }

    public List<Outbox> loadUnpublishedAndOlderThanOneMinute() {
        //
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        return this.outboxRepository.findUnPublishedEvents(calendar.getTimeInMillis());
    }

    @Transactional
    public void failPublish(String id) {
        Outbox outbox = loadOutbox(id);
        if(outbox == null) return;
        outbox.publish();
        this.outboxRepository.save(outbox);
    }

    @Transactional
    public void successPublish(String id) {
        Outbox outbox = loadOutbox(id);
        if(outbox == null) return;
        outbox.successPublish();
        this.outboxRepository.save(outbox);
    }
}
