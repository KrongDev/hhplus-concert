package com.hhplusconcert.infra.temporaryReservation.impl;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.TemporaryReservationJpoRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.jpo.TemporaryReservationJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TemporaryReservationRepositoryImpl implements TemporaryReservationRepository {
    //
    private final TemporaryReservationJpoRepository temporaryReservationJpoRepository;

    @Override
    public void save(TemporaryReservation temporaryReservation) {
        //
        this.temporaryReservationJpoRepository.save(new TemporaryReservationJpo(temporaryReservation));
    }

    @Override
    public List<TemporaryReservation> findByUserId(String userId) {
        //
        List<TemporaryReservationJpo> jpos = this.temporaryReservationJpoRepository.findAllByUserId(userId);
        return jpos.stream().map(TemporaryReservationJpo::toDomain).toList();
    }

    @Override
    public TemporaryReservation findById(String temporaryReservationId) {
        Optional<TemporaryReservationJpo> jpo = this.temporaryReservationJpoRepository.findById(temporaryReservationId);
        return jpo.map(TemporaryReservationJpo::toDomain).orElse(null);
    }

    @Override
    public void deleteByIds(List<String> temporaryReservationIds) {
        this.temporaryReservationJpoRepository.deleteAllById(temporaryReservationIds);
    }

    @Override
    public List<TemporaryReservation> findAllByDeleteAt(long deletedAt) {
        List<TemporaryReservationJpo> jpos = this.temporaryReservationJpoRepository.findAllByDeleteAtLessThanEqualAndPaidFalse(deletedAt);
        return jpos.stream().map(TemporaryReservationJpo::toDomain).toList();
    }
}
