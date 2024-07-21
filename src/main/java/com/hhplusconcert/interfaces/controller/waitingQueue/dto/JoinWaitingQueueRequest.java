package com.hhplusconcert.interfaces.controller.waitingQueue.dto;

import org.springframework.util.Assert;

public record JoinWaitingQueueRequest(String tokenId) {
    //
    public void validate() {
        //
        Assert.hasText(tokenId, "tokenId is required");
    }
}
