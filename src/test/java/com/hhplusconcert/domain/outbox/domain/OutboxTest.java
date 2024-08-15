package com.hhplusconcert.domain.outbox.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutboxTest {

    private Outbox outbox;

    @BeforeEach
    public void setUp() {
        this.outbox = new Outbox(
                "",
                "",
                "",
                "",
                0,
                false,
                false,
                0,
                0
        );
    }

    @Test
    @DisplayName("publish 시도시 count 증가 및 3번 시도시 skipped true")
    void publishFail() {
        outbox.publish();
        outbox.publish();
        outbox.publish();

        assertEquals(3, outbox.getCount());
        assertFalse(outbox.isPublished());
        assertTrue(outbox.isSkipped());
    }

    @Test
    @DisplayName("publish 시도시 count 증가")
    void successPublish() {
        outbox.successPublish();

        assertEquals(0, outbox.getCount());
        assertTrue(outbox.isPublished());
        assertFalse(outbox.isSkipped());
    }
}
