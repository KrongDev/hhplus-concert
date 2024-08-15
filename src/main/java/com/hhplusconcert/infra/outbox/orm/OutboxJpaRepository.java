package com.hhplusconcert.infra.outbox.orm;

import com.hhplusconcert.infra.outbox.orm.jpo.OutboxJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxJpo, String> {
    List<OutboxJpo> findAllByPublishedIsFalseAndSkippedIsFalseAndPublishedAtLessThanEqual(long pointAt);
}
