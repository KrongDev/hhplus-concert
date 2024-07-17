package com.hhplusconcert.interfaces.controller.point.command;

import org.springframework.util.Assert;

public record UsePointCommand (
        String userId,
        String paymentId,
        int amount
) {

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(paymentId, "paymentId is required");
        Assert.isTrue(amount > 0, "amount must be greater than 0");
    }
}
