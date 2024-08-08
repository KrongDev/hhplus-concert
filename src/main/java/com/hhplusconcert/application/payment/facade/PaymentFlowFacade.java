package com.hhplusconcert.application.payment.facade;

import com.hhplusconcert.common.annotation.LoggingPoint;
import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.payment.service.PaymentService;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import com.hhplusconcert.domain.point.service.PointService;
import com.hhplusconcert.domain.reservation.service.ReservationService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFlowFacade {
    //
    private final TemporaryReservationService temporaryReservationService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final WaitingTokenService waitingTokenService;

    @LoggingPoint
    @Transactional
    public String processTemporaryReservationPayment(
            String temporaryReservationId,
            String userId
    ) {
        TemporaryReservation temporaryReservation = this.temporaryReservationService.payReservation(temporaryReservationId);
        int price = temporaryReservation.getPrice();
        // 예약 테이블로 옮김
        String reservationId = this.reservationService.create(
                userId,
                temporaryReservation.getConcertId(),
                temporaryReservation.getTitle(),
                temporaryReservation.getSeriesId(),
                temporaryReservation.getSeatId(),
                temporaryReservation.getSeatRow(),
                temporaryReservation.getSeatCol(),
                price
        );
        // 결제 처리
        String paymentId = this.paymentService.create(reservationId, userId, price);
        //포인트 사용
        this.pointService.use(userId, paymentId, price);
        this.pointHistoryService.createPointHistory(userId, price, PointHistoryStatus.USE, paymentId);
        //
        try {
            this.waitingTokenService.deleteByUserIdAndSeriesId(userId, temporaryReservation.getSeriesId());
        } catch (CustomGlobalException e) {
            if (!ErrorType.TOKEN_NOT_FOUND.equals(e.getErrorType())) {
                throw e;
            } else {
                log.error(e.getErrorType().getReasonPhrase(), e);
            }
        }
        return paymentId;
    }
}
