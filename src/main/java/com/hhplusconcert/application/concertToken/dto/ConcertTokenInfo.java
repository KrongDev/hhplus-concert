package com.hhplusconcert.application.concertToken.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertTokenInfo {
    private String tokenId;
    private String userId;
    private String payload;
    private LocalDateTime lastUpdateAt;
    private LocalDateTime expiredAt;
    private LocalDateTime createAt;

    public static ConcertTokenInfo sample() {
        //
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusHours(1);
        return new ConcertTokenInfo(
            "",
            "",
            "",
            now,
            expiredAt,
            now
        );
    }
}
