package com.hhplusconcert.presentation.temporaryReservation.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTemporaryReservationCommand {
    //
    private String userId;
    private String concertId;
    private String seriesId;
    private String sheetId;

    public void validation() {
        Assert.hasText(userId, "userId is required");
        Assert.hasText(concertId, "concertId is required");
        Assert.hasText(seriesId, "seriesId is required");
        Assert.hasText(sheetId, "sheetId is required");
    }
}
