package com.hhplusconcert.domain.point.event;

public record UsedPoint(
        String requestUserId,
        String paymentId,
        int amount
) {

    public static UsedPoint on (String requestUserId, String paymentId, int amount) {
        return new UsedPoint(requestUserId, paymentId, amount);
    }
}
