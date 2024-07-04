package com.hhplusconcert.presentation.concertQueue.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinQueueCommand {
    private String tokenId;
    private String seriesId;

    public void validation() {
        Assert.hasText(tokenId, "tokenId is required");
    }
}
