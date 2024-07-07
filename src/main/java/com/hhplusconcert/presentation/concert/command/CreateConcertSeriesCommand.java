package com.hhplusconcert.presentation.concert.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConcertSeriesCommand {
    //
    private String concertId;
    private Long startAt;
    private Long endAt;
    private Long reserveStartAt;
    private Long reserveEndAt;
    private int maxRow;
    private int maxSeat;

    public void validation() {
        long now = System.currentTimeMillis();
        Assert.hasText(concertId, "concertId is required");
        Assert.isTrue(now < reserveEndAt, "reserveEndAt is invalid");
        Assert.isTrue(now < endAt, "endAt is invalid");
        Assert.isTrue(maxRow > 0, "maxRow is invalid");
        Assert.isTrue(maxSeat > 0 && maxSeat > maxRow, "maxSeat is invalid");
    }
}
