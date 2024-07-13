package com.hhplusconcert.application.concertQueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertQueueInfo {
    private String queueId;
    private String tokenId;
    private int count; // 순번
    private QueueStatus status;
    private Long createAt;

    public enum QueueStatus {
        READY,
        PROCESSING,
        END
    }

    public static ConcertQueueInfo sample() {
        return new ConcertQueueInfo(
                "",
                "",
                1,
                QueueStatus.PROCESSING,
                System.currentTimeMillis()
        );
    }
}
