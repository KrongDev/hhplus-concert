package com.hhplusconcert.domain.point.service;

import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryService {
    //
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void createPointHistory(
            String userId,
            int amount,
            PointHistoryStatus status,
            String paymentId
    ) {
        PointHistory pointHistory = PointHistory.newInstance(userId, amount, status, paymentId);
        pointHistoryRepository.save(pointHistory);
    }

    @Transactional
    public void createPointHistory(
            String userId,
            int amount,
            PointHistoryStatus status
    ) {
        PointHistory pointHistory = PointHistory.newInstance(userId, amount, status, "");
        pointHistoryRepository.save(pointHistory);
    }

    public List<PointHistory> loadPointHistories(String userId) {
        //
        return this.pointHistoryRepository.findAll(userId);
    }
}
