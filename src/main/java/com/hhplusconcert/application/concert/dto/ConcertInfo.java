package com.hhplusconcert.application.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertInfo {
    private String concertId;
    private String userId;
    private String title;
    private LocalDateTime createAt;

    public static ConcertInfo sample() {
        return new ConcertInfo(
                "ost_1",
                "krong",
                "진격의 거인 OST 콘서트",
                LocalDateTime.now()
        );
    }
}
