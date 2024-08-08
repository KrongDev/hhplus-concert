package com.hhplusconcert.domain.point.event;

public record ChargedPoint(
        String requestUserId,
        int amount
) {

    public static ChargedPoint on (String requestUserId, int amount) {
        return new ChargedPoint(requestUserId, amount);
    }
}
