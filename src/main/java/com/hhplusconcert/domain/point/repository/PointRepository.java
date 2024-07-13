package com.hhplusconcert.domain.point.repository;

import com.hhplusconcert.domain.point.model.Point;

public interface PointRepository {
    void save(Point point);
    Point findById(String userId);
}
