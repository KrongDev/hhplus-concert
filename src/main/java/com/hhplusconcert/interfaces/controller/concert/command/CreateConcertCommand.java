package com.hhplusconcert.interfaces.controller.concert.command;

import org.springframework.util.Assert;


public record CreateConcertCommand (
        String userId,
        String title
) {

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(title, "title is required");
    }
}
