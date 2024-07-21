package com.hhplusconcert.interfaces.controller.temporaryReservation.dto;

import org.springframework.util.Assert;


public record TemporaryReservationCreationRequest(
        String userId,
        String concertId,
        String seriesId,
        String seatId
) {

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(concertId, "concertId is required");
        Assert.hasText(seriesId, "seriesId is required");
        Assert.hasText(seatId, "seatId is required");
    }
}
