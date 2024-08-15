package com.hhplusconcert.domain.outbox.domain;

import com.hhplusconcert.common.util.JsonUtil;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Outbox {
    //
    private String id;
    private String payload;
    private boolean published;
    private boolean skipped;
    private long publishedAt;
    private long createdAt;

    public static Outbox of(
            Object message
    ) {
        String newId = UUID.randomUUID().toString();
        String payload = JsonUtil.toJson(message);
        return Outbox.builder()
                .id(newId)
                .payload(payload)
                .createdAt(System.currentTimeMillis())
                .build();
    }

    public void published() {
        this.published = true;
    }
}
