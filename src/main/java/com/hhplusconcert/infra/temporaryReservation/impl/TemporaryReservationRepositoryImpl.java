package com.hhplusconcert.infra.temporaryReservation.impl;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.TemporaryReservationJpoRepository;
import com.hhplusconcert.infra.temporaryReservation.orm.jpo.TemporaryReservationJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public TemporaryReservation findByIdAndNotPaidWithException(String temporaryReservationId) {
        TemporaryReservationJpo jpo = this.temporaryReservationJpoRepository.findByTemporaryReservationIdAndPaid(temporaryReservationId, false).orElseThrow();
        return jpo.toDomain();
    }

    @Override
    public TemporaryReservation findByIdWithException(String temporaryReservationId) {
        TemporaryReservationJpo jpo = this.temporaryReservationJpoRepository.findById(temporaryReservationId).orElseThrow();
        return jpo.toDomain();
    }
}
