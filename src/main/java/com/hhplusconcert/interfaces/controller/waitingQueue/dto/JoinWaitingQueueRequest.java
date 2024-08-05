package com.hhplusconcert.interfaces.controller.waitingQueue.dto;

import org.springframework.util.Assert;

public record JoinWaitingQueueRequest(String userId, String seriesId) {
    //
    public void validate() {
        //
        Assert.hasText(userId, "userId is required");
        Assert.hasText(seriesId, "seriesId is required");
    }
}
