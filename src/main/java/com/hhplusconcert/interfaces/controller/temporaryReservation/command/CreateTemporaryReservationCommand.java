package com.hhplusconcert.interfaces.controller.temporaryReservation.command;

import org.springframework.util.Assert;


public record CreateTemporaryReservationCommand(
        String userId,
        String concertId,
        String seriesId,
        String seatId
) {

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(concertId, "concertId is required");
        Assert.hasText(seriesId, "seriesId is required");
        Assert.hasText(seatId, "seatId is required");
    }
}
