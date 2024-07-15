package com.hhplusconcert.interfaces.controller.payment.rest;

import com.hhplusconcert.application.payment.facade.PaymentFlowFacade;
import com.hhplusconcert.application.payment.facade.PaymentSeekFacade;
import com.hhplusconcert.domain.payment.model.Payment;
import com.hhplusconcert.interfaces.controller.payment.command.SubmitPaymentCommand;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    //
    private final PaymentFlowFacade paymentFlowFacade;
    private final PaymentSeekFacade paymentSeekFacade;

    @PostMapping
    @Description("결제 요청")
    public ResponseEntity<String> submitPayment(@RequestBody SubmitPaymentCommand command) {
        //
        command.validation();
        String temporaryReservationId = command.getTemporaryReservationId();
        String userId = command.getUserId();
        return ResponseEntity.ok(this.paymentFlowFacade.processTemporaryReservationPayment(temporaryReservationId, userId));
    }

    @GetMapping()
    @Description("결제 내역 조회")
    public ResponseEntity<List<Payment>> loadPayment(@RequestParam String userId) {
        //
        return ResponseEntity.ok(this.paymentSeekFacade.loadPayments(userId));
    }

    @GetMapping("/{paymentId}")
    @Description("결제 조회")
    public ResponseEntity<Payment> loadPayments(@PathVariable String paymentId) {
        //
        return ResponseEntity.ok(this.paymentSeekFacade.loadPayment(paymentId));
    }

}
