package com.hhplusconcert.presentation.payment.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitPaymentCommand {
    private String temporaryReservationId;
    private String userId;

    public void validation() {
        //
        Assert.hasText(temporaryReservationId, "temporaryReservationId is required");
        Assert.hasText(userId, "userId is required");
    }
}
