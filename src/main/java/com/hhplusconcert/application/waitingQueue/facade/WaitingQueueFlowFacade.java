package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.common.dto.IdName;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingQueueFlowFacade {
    //
    private final WaitingQueueService waitingQueueService;
    private final WaitingTokenService waitingTokenService;


    public Long joinQueue(String userId, String seriesId) {
        //토큰 조회 없으면 에러
        if(this.waitingTokenService.alreadyToken(userId, seriesId))
            throw new CustomGlobalException(ErrorType.TOKEN_ALREADY_EXIST);
        this.waitingQueueService.create(userId, seriesId);
        return this.waitingQueueService.loadWaitingQueueCount(userId, seriesId);
    }

    public void activateWaitingQueueItems() {
        // WaitingToken 현재 활성화 갯수 체크
        Long joinCount = this.waitingTokenService.addTokensCount();
        if(joinCount == 0)
            return;
        List<WaitingQueue.WaitingQueueKey> keys = this.waitingQueueService.loadActiveWaitingQueues(joinCount);
        if(keys.isEmpty())
            return;
        keys.forEach(key -> {
            try {
                this.waitingTokenService.create(key.getUserId(), key.getSeriesId());
            } catch (CustomGlobalException e) {
                log.warn(e.getMessage(), e);
            }
        });
        //  WaitingToken 활성화 ( 생성하기 )
        this.waitingQueueService.deleteWaitingQueuesByRange(joinCount);
    }
}
