package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.application.waitingQueue.dto.WaitingDto;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueSeekFacade {
    //
    private final WaitingQueueService waitingQueueService;
    private final WaitingTokenService waitingTokenService;

    public WaitingDto loadNowWaitingCount(String userId, String seriesId) {
        //
        Long count = this.waitingQueueService.loadWaitingQueueCount(userId, seriesId);
        if(count == null) {
            try {
                WaitingToken token = this.waitingTokenService.loadWaitingToken(userId, seriesId);
                return WaitingDto.from(token);
            }catch (CustomGlobalException e) {
                throw new CustomGlobalException(ErrorType.WAITING_QUEUE_NOT_FOUND);
            }
        } else {
            return WaitingDto.from(count);
        }
    }
}
