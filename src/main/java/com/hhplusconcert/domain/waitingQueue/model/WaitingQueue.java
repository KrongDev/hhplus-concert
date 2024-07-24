package com.hhplusconcert.domain.waitingQueue.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
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
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.MINUTE, 30);
        Long expireAt = calendar.getTimeInMillis();
        return WaitingQueue.builder()
                .tokenId(tokenId)
                .createAt(now)
                .expiredAt(expireAt)
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

    public void ended() {
        this.status = WaitingQueueStatus.END;
        this.updateAt = System.currentTimeMillis();
    }

    public boolean isEnded() {
        //
        return this.status == WaitingQueueStatus.END;
    }

    public boolean isProcess() {
        //
        return this.status == WaitingQueueStatus.PROCESS;
    }

    public void verifyQueueStatusReady() {
        if(this.isEnded())
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_EXPIRED);
        if(this.isProcess())
            throw new CustomGlobalException(ErrorType.WAITING_QUEUE_PROCESSING);
    }
}
