package com.hhplusconcert.infra.reservation.impl;

import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.domain.reservation.repository.ReservationRepository;
import com.hhplusconcert.infra.reservation.orm.ReservationJpaRepository;
import com.hhplusconcert.infra.reservation.orm.jpo.ReservationJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    //
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public void save(Reservation reservation) {
        //
        this.reservationJpaRepository.save(new ReservationJpo(reservation));
    }

    @Override
    public Reservation findById(String id) {
        //
        Optional<ReservationJpo> jpo = this.reservationJpaRepository.findById(id);
        return jpo.map(ReservationJpo::toDomain).orElse(null);
    }

    @Override
    public List<Reservation> findAllByUserId(String userId) {
        List<ReservationJpo> jpos = this.reservationJpaRepository.findAllByUserId(userId);
        return jpos.stream().map(ReservationJpo::toDomain).toList();
    }
}
