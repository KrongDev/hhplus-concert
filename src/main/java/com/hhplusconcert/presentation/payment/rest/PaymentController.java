package com.hhplusconcert.presentation.payment.rest;

import com.hhplusconcert.application.payment.dto.PaymentInfo;
import com.hhplusconcert.presentation.payment.command.SubmitPaymentCommand;
import jdk.jfr.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping
    @Description("결제 요청")
    public ResponseEntity<String> submitPayment(@RequestBody SubmitPaymentCommand command) {
        //
        command.validation();
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @GetMapping()
    @Description("결제 내역 조회")
    public ResponseEntity<List<PaymentInfo>> loadPayment(@RequestParam String userId) {
        //
        return ResponseEntity.ok(List.of(PaymentInfo.sample()));
    }

    @GetMapping("/{paymentId}")
    @Description("결제 조회")
    public ResponseEntity<PaymentInfo> loadPayments(@PathVariable String paymentId) {
        //
        return ResponseEntity.ok(PaymentInfo.sample());
    }

}
