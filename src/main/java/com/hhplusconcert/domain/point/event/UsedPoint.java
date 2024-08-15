package com.hhplusconcert.domain.point.event;

import com.hhplusconcert.domain.common.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  UsedPoint extends Event {
        private String eventId;
        private String requestUserId;
        private String  paymentId;
        private int amount;

    public static UsedPoint of(String requestUserId, String paymentId, int amount) {
        String eventId = UUID.randomUUID().toString();
        return new UsedPoint(eventId, requestUserId, paymentId, amount);
    }
}
