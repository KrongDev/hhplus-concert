package com.hhplusconcert.domain.point.service;

import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PointService {
    //
    private final PointRepository pointRepository;

    @Transactional
    public void create(String userId) {
        //
        this.pointRepository.save(Point.newInstance(userId, 0));
    }

    @Transactional
    public Point loadPoint(String userId) {
        //
        Point point = this.pointRepository.findById(userId);
        if(Objects.isNull(point))
            this.create(userId);
        return this.pointRepository.findById(userId);
    }

    @Transactional
    public void charge(String userId, int amount) {
        Point point = this.loadPoint(userId);
        point.charge(amount);
        this.pointRepository.save(point);
    }

    @Transactional
    public void use(String userId, int amount) {
        Point point = this.loadPoint(userId);
        point.use(amount);
        this.pointRepository.save(point);
    }
}
