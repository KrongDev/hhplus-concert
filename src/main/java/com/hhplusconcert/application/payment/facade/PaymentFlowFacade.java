package com.hhplusconcert.application.payment.facade;

import com.hhplusconcert.common.annotation.LoggingPoint;
import com.hhplusconcert.domain.payment.event.PaymentConfirmed;
import com.hhplusconcert.domain.payment.service.PaymentService;
import com.hhplusconcert.domain.point.service.PointService;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.service.TemporaryReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFlowFacade {
    //
    private final TemporaryReservationService temporaryReservationService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final ApplicationEventPublisher eventPublisher;

    @LoggingPoint
    @Transactional
    public String processTemporaryReservationPayment(
            String temporaryReservationId,
            String userId
    ) {
        TemporaryReservation temporaryReservation = this.temporaryReservationService.loadTemporaryReservation(temporaryReservationId);
        int price = temporaryReservation.getPrice();
        // 결제 처리
        String paymentId = this.paymentService.create(userId, price);
        this.temporaryReservationService.payReservation(temporaryReservationId);
        //포인트 사용
        this.pointService.use(userId, paymentId, price);
        this.eventPublisher.publishEvent(PaymentConfirmed.of(
                temporaryReservation.getTemporaryReservationId(),
                temporaryReservation.getUserId(),
                temporaryReservation.getConcertId(),
                temporaryReservation.getTitle(),
                temporaryReservation.getSeriesId(),
                temporaryReservation.getSeatId(),
                temporaryReservation.getSeatRow(),
                temporaryReservation.getSeatCol(),
                temporaryReservation.getPrice(),
                paymentId
        ));
        return paymentId;
    }
}
