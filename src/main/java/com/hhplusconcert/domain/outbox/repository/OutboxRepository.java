package com.hhplusconcert.domain.outbox.repository;

import com.hhplusconcert.domain.outbox.domain.Outbox;

import java.util.List;

public interface OutboxRepository {
    Outbox findOutbox (String id);
    List<Outbox> findUnPublishedEvents (long pointAt);
    void save (Outbox outbox);
}
