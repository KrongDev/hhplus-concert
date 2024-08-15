package com.hhplusconcert.domain.common;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Event {
    private final String eventId;
    private final long createdAt;

    public Event() {
        this.eventId = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
    }
}
