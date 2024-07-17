package com.hhplusconcert.interfaces.controller.waitingToken.command;

import org.springframework.util.Assert;

public record CreateWaitingTokenCommand (
        String userId,
        String seriesId
) {
    //

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(seriesId, "seriesId is required");
    }
}
