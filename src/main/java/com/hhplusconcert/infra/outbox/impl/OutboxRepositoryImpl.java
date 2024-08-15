package com.hhplusconcert.infra.outbox.impl;

import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.repository.OutboxRepository;
import com.hhplusconcert.infra.outbox.orm.OutboxJpaRepository;
import com.hhplusconcert.infra.outbox.orm.jpo.OutboxJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
    //
    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public List<Outbox> findUnPublishedEvents() {
        List<OutboxJpo> jpos = this.outboxJpaRepository.findAllByPublishedIsFalseAndSkippedIsFalse();
        return jpos.stream().map(OutboxJpo::toDomain).toList();
    }

    @Override
    public void save(Outbox outbox) {
        //
        this.outboxJpaRepository.save(OutboxJpo.fromDomain(outbox));
    }
}
