package com.hhplusconcert.domain.outbox.repository;

import com.hhplusconcert.domain.outbox.domain.Outbox;

import java.util.List;

public interface OutboxRepository {
    List<Outbox> findUnPublishedEvents ();
    void save (Outbox outbox);
}
