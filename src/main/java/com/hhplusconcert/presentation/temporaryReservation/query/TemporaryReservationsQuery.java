package com.hhplusconcert.presentation.temporaryReservation.query;

import org.springframework.util.Assert;

public record TemporaryReservationsQuery(String userId) {

    public void validate() {
        Assert.hasText(userId, "userId is required");
    }
}
