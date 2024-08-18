package com.hhplusconcert.domain.point.event;

import com.hhplusconcert.domain.common.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  UsedPoint extends Event {
    public static final String topicId = "used_point";
        private String requestUserId;
        private String  paymentId;
        private int amount;

    public static UsedPoint of(String requestUserId, String paymentId, int amount) {
        return new UsedPoint(requestUserId, paymentId, amount);
    }
}
