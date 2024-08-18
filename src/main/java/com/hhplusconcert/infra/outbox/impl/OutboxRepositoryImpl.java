package com.hhplusconcert.infra.outbox.impl;

import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.domain.outbox.repository.OutboxRepository;
import com.hhplusconcert.infra.outbox.orm.OutboxJpaRepository;
import com.hhplusconcert.infra.outbox.orm.jpo.OutboxJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
    //
    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public Outbox findOutbox(String id) {
        Optional<OutboxJpo> jpo = this.outboxJpaRepository.findById(id);
        return jpo.map(OutboxJpo::toDomain).orElse(null);
    }

    @Override
    public List<Outbox> findUnPublishedEvents(long pointAt) {
        List<OutboxJpo> jpos = this.outboxJpaRepository.findAllByPublishedIsFalseAndSkippedIsFalseAndPublishedAtLessThanEqual(pointAt);
        return jpos.stream().map(OutboxJpo::toDomain).toList();
    }

    @Override
    public void save(Outbox outbox) {
        //
        this.outboxJpaRepository.save(OutboxJpo.fromDomain(outbox));
    }
}
