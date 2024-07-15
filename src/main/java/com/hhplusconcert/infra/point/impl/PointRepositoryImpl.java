package com.hhplusconcert.infra.point.impl;

import com.hhplusconcert.domain.common.exception.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.ErrorType;
import com.hhplusconcert.domain.point.model.Point;
import com.hhplusconcert.domain.point.repository.PointRepository;
import com.hhplusconcert.infra.point.orm.PointJpoRepository;
import com.hhplusconcert.infra.point.orm.jpo.PointJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {
    //
    private final PointJpoRepository pointJpoRepository;


    @Override
    public void save(Point point) {
        //
        this.pointJpoRepository.save(new PointJpo(point));
    }

    @Override
    public Point findByIdWithThrow(String userId) {
        Optional<PointJpo> jpo = this.pointJpoRepository.findById(userId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.POINT_NOT_FOUND);
        return jpo.get().toDomain();
    }
}
