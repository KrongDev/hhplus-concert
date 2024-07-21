package com.hhplusconcert.infra.point.orm;

import com.hhplusconcert.infra.point.orm.jpo.PointJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<PointJpo, String> {
}
