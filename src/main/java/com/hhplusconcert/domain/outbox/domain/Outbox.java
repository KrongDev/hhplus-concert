package com.hhplusconcert.domain.outbox.domain;

import com.hhplusconcert.common.util.JsonUtil;
import com.hhplusconcert.domain.common.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Outbox {
    //
    private String id;
    private String topic;
    private String classPath;
    private String payload;
    private int count;
    // 발행요청 시각 - 마지막 발행 시도 시각을 의미
    private boolean published;
    private boolean skipped;
    private long publishedAt;
    private long createdAt;

    public static <T extends Event> Outbox from(
            String topicId,
            T message
    ) {
        String payload = JsonUtil.toJson(message);
        long now = System.currentTimeMillis();
        return Outbox.builder()
                .id(message.getEventId())
                .topic(topicId)
                .classPath(message.getClass().getName())
                .payload(payload)
                .createdAt(now)
                .publishedAt(now)
                .build();
    }

    public void publish() {
        this.count++;
        if(this.count == 3)
            this.skipped = true;
        this.publishedAt = System.currentTimeMillis();
    }

    public void successPublish() {
        this.published = true;
        this.publishedAt = System.currentTimeMillis();
    }

}
