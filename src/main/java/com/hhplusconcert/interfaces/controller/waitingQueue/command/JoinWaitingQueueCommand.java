package com.hhplusconcert.interfaces.controller.waitingQueue.command;

import org.springframework.util.Assert;

public record JoinWaitingQueueCommand(String tokenId) {
    //
    public void validate() {
        //
        Assert.hasText(tokenId, "tokenId is required");
    }
}
