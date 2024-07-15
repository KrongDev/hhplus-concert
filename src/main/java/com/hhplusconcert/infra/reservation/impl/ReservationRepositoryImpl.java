package com.hhplusconcert.infra.reservation.impl;

import com.hhplusconcert.domain.common.exception.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.ErrorType;
import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.repository.ReservationRepository;
import com.hhplusconcert.infra.reservation.orm.ReservationJpoRepository;
import com.hhplusconcert.infra.reservation.orm.jpo.ReservationJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    //
    private final ReservationJpoRepository reservationJpoRepository;

    @Override
    public void save(Reservation reservation) {
        //
        this.reservationJpoRepository.save(new ReservationJpo(reservation));
    }

    @Override
    public Reservation findByIdWithThrow(String id) {
        //
        Optional<ReservationJpo> jpo = this.reservationJpoRepository.findById(id);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.RESERVATION_NOT_FOUND);
        return jpo.get().toDomain();
    }

    @Override
    public List<Reservation> findAllByUserId(String userId) {
        List<ReservationJpo> jpos = this.reservationJpoRepository.findAllByUserId(userId);
        return jpos.stream().map(ReservationJpo::toDomain).toList();
    }
}
