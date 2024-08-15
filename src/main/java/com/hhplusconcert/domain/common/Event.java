package com.hhplusconcert.domain.common;

import java.util.UUID;

public abstract class Event {
    private final String eventId;

    public Event() {
        this.eventId = UUID.randomUUID().toString();
    }

    public String getEventId() {
        return eventId;
    }
}
