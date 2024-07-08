package com.hhplusconcert.infra.reservation.impl;

import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.repository.ReservationRepository;
import com.hhplusconcert.infra.reservation.orm.ReservationJpoRepository;
import com.hhplusconcert.infra.reservation.orm.jpo.ReservationJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        ReservationJpo jpo = this.reservationJpoRepository.findById(id).orElseThrow();
        return jpo.toDomain();
    }

    @Override
    public List<Reservation> findAllByUserId(String userId) {
        List<ReservationJpo> jpos = this.reservationJpoRepository.findAllByUserId(userId);
        return jpos.stream().map(ReservationJpo::toDomain).toList();
    }
}
