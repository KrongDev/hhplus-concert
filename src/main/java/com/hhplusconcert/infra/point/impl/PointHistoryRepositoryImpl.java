package com.hhplusconcert.infra.point.impl;

import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.domain.point.repository.PointHistoryRepository;
import com.hhplusconcert.infra.point.orm.PointHistoryJpaRepository;
import com.hhplusconcert.infra.point.orm.jpo.PointHistoryJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    //
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public void save(PointHistory pointHistory) {
        //
        this.pointHistoryJpaRepository.save(new PointHistoryJpo(pointHistory));
    }

    @Override
    public List<PointHistory> findAll(String userId) {
        List<PointHistoryJpo> histories = this.pointHistoryJpaRepository.findAllByUserId(userId);
        return histories.stream().map(PointHistoryJpo::toDomain).toList();
    }
}
