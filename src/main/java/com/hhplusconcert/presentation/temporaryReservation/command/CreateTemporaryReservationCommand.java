package com.hhplusconcert.presentation.temporaryReservation.command;

import org.springframework.util.Assert;


public record CreateTemporaryReservationCommand(
        String userId,
        String concertId,
        String seriesId,
        int seatRow,
        int seatCol
) {

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(concertId, "concertId is required");
        Assert.hasText(seriesId, "seriesId is required");
    }
}
