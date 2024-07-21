package com.hhplusconcert.interfaces.controller.waitingToken.dto;

import org.springframework.util.Assert;

public record WaitingTokenCreationRequest(
        String userId,
        String seriesId
) {
    //

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(seriesId, "seriesId is required");
    }
}
