package com.hhplusconcert.domain.concert.model;

import com.hhplusconcert.domain.concert.model.vo.ConcertSeriesStatus;
import lombok.*;


import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeries {
    private String seriesId;
    private String concertId;
    private Long startAt;
    private Long endAt;
    private Long reserveStartAt;
    private Long reserveEndAt;
    private ConcertSeriesStatus status;
    private Long createAt;

    public static ConcertSeries newInstance(
            String concertId,
            Long startAt,
            Long endAt,
            Long reserveStartAt,
            Long reserveEndAt
    ) {
        String newId = UUID.randomUUID().toString();
        Long now = System.currentTimeMillis();
        ConcertSeriesStatus status = ConcertSeriesStatus.READY;
        if(now > reserveStartAt) status = ConcertSeriesStatus.PROCESSING;

        return ConcertSeries.builder()
                .seriesId(newId)
                .concertId(concertId)
                .startAt(startAt)
                .endAt(endAt)
                .reserveStartAt(reserveStartAt)
                .reserveEndAt(reserveEndAt)
                .status(status)
                .createAt(now)
                .build();
    }
}
