package com.hhplusconcert.domain.point.event;

public record ChargedPoint(
        String requestUserId,
        int amount
) {

    public static ChargedPoint of(String requestUserId, int amount) {
        return new ChargedPoint(requestUserId, amount);
    }
}
