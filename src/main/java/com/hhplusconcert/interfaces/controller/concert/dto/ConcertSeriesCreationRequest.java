package com.hhplusconcert.interfaces.controller.concert.dto;

import org.springframework.util.Assert;



public record ConcertSeriesCreationRequest(
        String concertId,
        Long startAt,
        Long endAt,
        Long reserveStartAt,
        Long reserveEndAt,
        int maxRow,
        int maxSeat
) {

    public void validate() {
        long now = System.currentTimeMillis();
        Assert.hasText(concertId, "concertId is required");
        Assert.isTrue(now < reserveEndAt, "reserveEndAt is invalid");
        Assert.isTrue(now < endAt, "endAt is invalid");
        Assert.isTrue(maxRow > 0, "maxRow is invalid");
        Assert.isTrue(maxSeat > 0 && maxSeat > maxRow, "maxSeat is invalid");
    }
}
