package com.hhplusconcert.presentation.point.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsePointCommand {
    private String userId;
    private String paymentId;
    private int amount;

    public void validate() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(paymentId, "paymentId is required");
        Assert.isTrue(amount > 0, "amount must be greater than 0");
    }
}
