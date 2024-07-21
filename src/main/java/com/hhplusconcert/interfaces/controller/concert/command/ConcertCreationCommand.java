package com.hhplusconcert.interfaces.controller.concert.command;

import org.springframework.util.Assert;


public record ConcertCreationCommand(
        String userId,
        String title
) {

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(title, "title is required");
    }
}
