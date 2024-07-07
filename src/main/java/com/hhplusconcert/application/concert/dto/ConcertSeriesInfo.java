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
public class ConcertSeriesInfo {
    private String seriesId;
    private String concertId;
    private LocalDateTime startAt; // 공연 시작시간
    private LocalDateTime endAt;
    private LocalDateTime reserveStartAt;
    private LocalDateTime reserveEndAt;
    private SeriesEnum status;
    private LocalDateTime createAt;

    public enum SeriesEnum {
        READY,
        PROCESSING,
        END
    }

    public static ConcertSeriesInfo sample() {
        return new ConcertSeriesInfo(
                "",
                "",
                LocalDateTime.of(2024, 7, 12, 12, 0),
                LocalDateTime.of(2024, 7, 12, 15, 0),
                LocalDateTime.of(2024, 7, 4, 12, 0),
                LocalDateTime.of(2024, 7, 7, 12, 0),
                SeriesEnum.PROCESSING,
                LocalDateTime.of(2024, 5, 12, 12, 0)
        );
    }
}
