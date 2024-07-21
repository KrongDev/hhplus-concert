package com.hhplusconcert.interfaces.controller.payment.dto;

import org.springframework.util.Assert;

public record SubmitPaymentRequest(
        String temporaryReservationId,
        String userId
) {

    public void validate() {
        //
        Assert.hasText(temporaryReservationId, "temporaryReservationId is required");
        Assert.hasText(userId, "userId is required");
    }
}
