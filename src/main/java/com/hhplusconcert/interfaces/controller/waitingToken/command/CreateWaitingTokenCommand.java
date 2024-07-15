package com.hhplusconcert.interfaces.controller.waitingToken.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWaitingTokenCommand {
    //
    private String userId;
    private String seriesId;

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(seriesId, "seriesId is required");
    }
}
