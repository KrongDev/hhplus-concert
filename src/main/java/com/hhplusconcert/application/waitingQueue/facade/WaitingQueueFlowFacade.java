package com.hhplusconcert.application.waitingQueue.facade;

import com.hhplusconcert.common.dto.IdName;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WaitingQueueFlowFacade {
    //
    private final WaitingQueueService waitingQueueService;
    private final WaitingTokenService waitingTokenService;


    public Long joinQueue(String userId, String seriesId) {
        //토큰 조회 없으면 에러
        this.waitingQueueService.create(userId, seriesId);
        return this.waitingQueueService.loadWaitingQueueCount(userId, seriesId);
    }

    public void activateWaitingQueueItems() {
        // WaitingToken 현재 활성화 갯수 체크
        Long joinCount = this.waitingTokenService.addTokensCount();
        List<WaitingQueue.WaitingQueueKey> keys = this.waitingQueueService.loadActiveWaitingQueues(joinCount);
        if(keys.isEmpty())
            return;
        List<IdName> idNames = keys.stream().map(key->new IdName(key.getUserId(), key.getSeriesId())).toList();
        //  WaitingToken 활성화 ( 생성하기 )
        this.waitingTokenService.createAll(idNames);
        this.waitingQueueService.deleteWaitingQueues(keys);
    }
}
