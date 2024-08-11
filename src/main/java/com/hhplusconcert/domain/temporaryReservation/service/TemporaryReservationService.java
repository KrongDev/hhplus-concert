package com.hhplusconcert.domain.temporaryReservation.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TemporaryReservationService {
    //
    private final TemporaryReservationRepository temporaryReservationRepository;

    @Transactional
    public String create(
            String userId,
            String concertId,
            String title,
            String seriesId,
            String seatId,
            int seatRow,
            int seatCol,
            int price
    ) {
        TemporaryReservation temporaryReservation = TemporaryReservation.newInstance(userId, concertId, title, seriesId, seatId, seatRow, seatCol, price);
        this.temporaryReservationRepository.save(temporaryReservation);
        return temporaryReservation.getTemporaryReservationId();
    }

    public List<TemporaryReservation> loadTemporaryReservations(String userId) {
        //
        return this.temporaryReservationRepository.findByUserId(userId);
    }

    public List<TemporaryReservation> loadExpiredTemporaryReservations() {
        //
        long now = System.currentTimeMillis();
        return this.temporaryReservationRepository.findAllByDeleteAt(now);
    }

    public TemporaryReservation loadTemporaryReservation(String temporaryReservationId) {
        //
        TemporaryReservation temporaryReservation = this.temporaryReservationRepository.findById(temporaryReservationId);
        if(Objects.isNull(temporaryReservation))
            throw new CustomGlobalException(ErrorType.TEMPORARY_RESERVATION_NOT_FOUND);
        return temporaryReservation;
    }

    @Transactional
    public void payReservation(String temporaryReservationId) {
        //
        TemporaryReservation temporaryReservation = this.loadTemporaryReservation(temporaryReservationId);
        temporaryReservation.finalizeConcertReservation();
        this.update(temporaryReservation);
    }

    @Transactional
    public List<String> expireReservationsAndReturnSeatIds() {
        // 시간 지난 임시 예약정보들 조회
        List<TemporaryReservation> temporaryReservations = this.loadExpiredTemporaryReservations();
        List<String> temporaryReservationIds = temporaryReservations.stream().map(TemporaryReservation::getTemporaryReservationId).toList();
        // 시간 지난 임시 예약 정보들 삭제
        this.deleteIds(temporaryReservationIds);
        // 취소한 시트 아이디들 반환
        return temporaryReservations.stream().map(TemporaryReservation::getSeatId).toList();
    }

    @Transactional
    public void update(TemporaryReservation temporaryReservation) {
        //
        this.temporaryReservationRepository.save(temporaryReservation);
    }

    @Transactional
    public void deleteIds(List<String> temporaryReservationIds) {
        //
        this.temporaryReservationRepository.deleteByIds(temporaryReservationIds);
    }
}
