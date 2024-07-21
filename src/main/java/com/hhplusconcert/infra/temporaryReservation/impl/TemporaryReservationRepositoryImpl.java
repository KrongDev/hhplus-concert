package com.hhplusconcert.infra.temporaryReservation.impl;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.TemporaryReservationJpaRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.jpo.TemporaryReservationJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TemporaryReservationRepositoryImpl implements TemporaryReservationRepository {
    //
    private final TemporaryReservationJpaRepository temporaryReservationJpaRepository;

    @Override
    public void save(TemporaryReservation temporaryReservation) {
        //
        this.temporaryReservationJpaRepository.save(new TemporaryReservationJpo(temporaryReservation));
    }

    @Override
    public List<TemporaryReservation> findByUserId(String userId) {
        //
        List<TemporaryReservationJpo> jpos = this.temporaryReservationJpaRepository.findAllByUserId(userId);
        return jpos.stream().map(TemporaryReservationJpo::toDomain).toList();
    }

    @Override
    public TemporaryReservation findById(String temporaryReservationId) {
        Optional<TemporaryReservationJpo> jpo = this.temporaryReservationJpaRepository.findById(temporaryReservationId);
        return jpo.map(TemporaryReservationJpo::toDomain).orElse(null);
    }

    @Override
    public void deleteByIds(List<String> temporaryReservationIds) {
        this.temporaryReservationJpaRepository.deleteAllById(temporaryReservationIds);
    }

    @Override
    public List<TemporaryReservation> findAllByDeleteAt(long deletedAt) {
        List<TemporaryReservationJpo> jpos = this.temporaryReservationJpaRepository.findAllByDeleteAtLessThanEqualAndPaidFalse(deletedAt);
        return jpos.stream().map(TemporaryReservationJpo::toDomain).toList();
    }
}
