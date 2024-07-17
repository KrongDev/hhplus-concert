package com.hhplusconcert.application.payment.facade;

import com.hhplusconcert.domain.payment.service.PaymentService;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
import com.hhplusconcert.domain.point.service.PointHistoryService;
import com.hhplusconcert.domain.point.service.PointService;
import com.hhplusconcert.domain.reservation.service.ReservationService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.service.WaitingQueueService;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.service.WaitingTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentFlowFacade {
    //
    private final TemporaryReservationService temporaryReservationService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final WaitingTokenService waitingTokenService;
    private final WaitingQueueService waitingQueueService;

    @Transactional
    public String processTemporaryReservationPayment(
            String temporaryReservationId,
            String userId
    ) {
        TemporaryReservation temporaryReservation = this.temporaryReservationService.loadTemporaryReservation(temporaryReservationId);
        this.temporaryReservationService.payReservation(temporaryReservationId);
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
        this.pointService.use(userId, price);
        this.pointHistoryService.createPointHistory(userId, price, PointHistoryStatus.USE, paymentId);
        //결제 완료
        WaitingToken token = this.waitingTokenService.loadWaitingToken(userId, temporaryReservation.getSeriesId());
        // 대기열 토큰 만료 처리
        this.waitingTokenService.deleteWaitingToken(token.getTokenId());
        WaitingQueue queue = this.waitingQueueService.loadWaitingQueue(token.getTokenId());
        queue.ended();
        this.waitingQueueService.update(queue);

        return paymentId;
    }
}
