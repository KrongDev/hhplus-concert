package com.hhplusconcert.presentation.concert.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConcertSeriesCommand {
    //
    private String concertId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime reserveStartAt;
    private LocalDateTime reserveEndAt;

    public void validation() {
        LocalDateTime now = LocalDateTime.now();
        Assert.hasText(concertId, "concertId is required");
        Assert.isTrue(now.isBefore(reserveEndAt), "reserveEndAt is invalid");
        Assert.isTrue(now.isBefore(endAt), "endAt is invalid");
    }
}
