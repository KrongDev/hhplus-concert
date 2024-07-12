package com.hhplusconcert.domain.waitingQueue.model;

import com.hhplusconcert.domain.waitingQueue.model.vo.WaitingQueueStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Calendar;

@Getter
@Builder
public class WaitingQueue {
    // 대기열 체크 번호
    private Long waitingQueueId;
    private String tokenId;
    private Long createAt;
    private Long updateAt;
    private Long expiredAt;

    private WaitingQueueStatus status;
//    private Long expiredAt;

    public static WaitingQueue newInstance(
            String tokenId
    ) {
        return WaitingQueue.builder()
                .tokenId(tokenId)
                .createAt(System.currentTimeMillis())
                .status(WaitingQueueStatus.READY)
                .build();
    }

    public void processToken() {
        //
        Calendar calendar = Calendar.getInstance();

        this.status = WaitingQueueStatus.PROCESS;
        this.updateAt = calendar.getTimeInMillis();

        calendar.add(Calendar.MINUTE, 5);
        this.updateAt = calendar.getTimeInMillis();
    }

    public void expired() {
        this.status = WaitingQueueStatus.EXPIRED;
        this.updateAt = System.currentTimeMillis();
    }

    public void ended() {
        this.status = WaitingQueueStatus.END;
        this.updateAt = System.currentTimeMillis();
    }
}
