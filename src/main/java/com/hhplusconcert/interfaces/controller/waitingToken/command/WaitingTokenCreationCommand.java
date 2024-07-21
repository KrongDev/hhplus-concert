package com.hhplusconcert.interfaces.controller.waitingToken.command;

import org.springframework.util.Assert;

public record WaitingTokenCreationCommand(
        String userId,
        String seriesId
) {
    //

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(seriesId, "seriesId is required");
    }
}
