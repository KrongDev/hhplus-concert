package com.hhplusconcert.domain.waitingQueue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Calendar;

@Getter
@Builder
public class WaitingQueue {
    // 대기열 체크 번호
    private WaitingQueueKey key;
    private Long createAt;
//    private Long updateAt;
//    private Long expiredAt;

//    private WaitingQueueStatus status;

    public static WaitingQueue newInstance(
            String userId,
            String seriesId
    ) {
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.MINUTE, 30);
        return WaitingQueue.builder()
                .key(new WaitingQueueKey(userId, seriesId))
                .createAt(now)
                .build();
    }
    @Data
    @AllArgsConstructor
    public static class WaitingQueueKey implements Serializable {
        //
        private String userId;
        private String seriesId;

        public WaitingQueueKey(String key) {
            String[] split = key.split(":");
            this.userId = split[0];
            this.seriesId = split[1];
        }

        @Override
        public String toString() {
            return this.userId + ":" + this.seriesId;
        }
    }
}
