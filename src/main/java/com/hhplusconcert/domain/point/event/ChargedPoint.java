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
public class ChargedPoint extends Event {
    private String requestUserId;
    private int amount;

    public static ChargedPoint of(String requestUserId, int amount) {
        return new ChargedPoint(requestUserId, amount);
    }
}
