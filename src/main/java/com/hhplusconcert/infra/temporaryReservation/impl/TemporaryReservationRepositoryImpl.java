package com.hhplusconcert.infra.temporaryReservation.impl;

import com.hhplusconcert.common.exception.model.CustomGlobalException;
import com.hhplusconcert.common.exception.model.vo.ErrorType;
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
    public TemporaryReservation findByIdAndNotPaidWithThrow(String temporaryReservationId) {
        Optional<TemporaryReservationJpo> jpo = this.temporaryReservationJpoRepository.findByTemporaryReservationIdAndPaid(temporaryReservationId, false);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.TEMPORARY_RESERVATION_NOT_FOUND);
        return jpo.get().toDomain();
    }

    @Override
    public TemporaryReservation findByIdWithException(String temporaryReservationId) {
        Optional<TemporaryReservationJpo> jpo = this.temporaryReservationJpoRepository.findById(temporaryReservationId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.TEMPORARY_RESERVATION_NOT_FOUND);
        return jpo.get().toDomain();
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
