package com.hhplusconcert.infra.waitingToken.orm;

import com.hhplusconcert.infra.waitingToken.orm.jpo.WaitingTokenJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitingTokenJpoRepository extends JpaRepository<WaitingTokenJpo, String> {
    Optional<WaitingTokenJpo> findByUserIdAndSeriesId(String userId, String seriesId);
}
