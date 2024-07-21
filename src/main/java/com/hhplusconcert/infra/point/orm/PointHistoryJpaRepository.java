package com.hhplusconcert.infra.point.orm;

import com.hhplusconcert.infra.point.orm.jpo.PointHistoryJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistoryJpo, String> {
    //
    List<PointHistoryJpo> findAllByUserId(String userId);
}
