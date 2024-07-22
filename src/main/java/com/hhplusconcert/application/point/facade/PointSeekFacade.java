package com.hhplusconcert.application.point.facade;

import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import com.hhplusconcert.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointSeekFacade {
    //
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;


    public Point loadPoint(String userId) {
        //
        return this.pointService.loadPoint(userId);
    }

    public List<PointHistory> loadPointHistoryByPointId(String userId) {
        //
        return this.pointHistoryService.loadPointHistories(userId);
    }
}
