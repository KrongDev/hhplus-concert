package com.hhplusconcert.interfaces.controller.concert.dto;

import org.springframework.util.Assert;


public record ConcertCreationRequest(
        String userId,
        String title
) {

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(title, "title is required");
    }
}
