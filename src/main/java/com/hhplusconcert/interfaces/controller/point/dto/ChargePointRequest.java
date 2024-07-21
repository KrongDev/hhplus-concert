package com.hhplusconcert.interfaces.controller.point.dto;

import org.springframework.util.Assert;

public record ChargePointRequest(
        String userId,
        int amount
) {

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.isTrue(amount > 0, "amount must be greater than 0");
    }
}
