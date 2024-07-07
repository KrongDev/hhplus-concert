package com.hhplusconcert.application.concertToken.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertTokenInfo {
    private String tokenId;
    private String userId;
    private String payload;
    private Long lastUpdateAt;
    private Long expiredAt;
    private Long createAt;

    public static ConcertTokenInfo sample() {
        //
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        Long expiredAt = calendar.getTimeInMillis();
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
