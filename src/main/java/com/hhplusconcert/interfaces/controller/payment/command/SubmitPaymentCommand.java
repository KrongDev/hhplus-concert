package com.hhplusconcert.interfaces.controller.payment.command;

import org.springframework.util.Assert;

public record SubmitPaymentCommand (
        String temporaryReservationId,
        String userId
) {

    public void validation() {
        //
        Assert.hasText(temporaryReservationId, "temporaryReservationId is required");
        Assert.hasText(userId, "userId is required");
    }
}
