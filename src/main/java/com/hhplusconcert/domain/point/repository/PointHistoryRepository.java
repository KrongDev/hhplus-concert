package com.hhplusconcert.domain.point.repository;

import com.hhplusconcert.domain.point.model.PointHistory;

import java.util.List;

public interface PointHistoryRepository {

    void save(PointHistory pointHistory);
    List<PointHistory> findAll(String userId);
}
