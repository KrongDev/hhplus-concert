package com.hhplusconcert.interfaces.controller.concert.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConcertCommand {
    //
    private String userId;
    private String title;

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(title, "title is required");
    }
}
