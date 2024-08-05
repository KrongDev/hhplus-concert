package com.hhplusconcert.domain.concert.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeries implements Serializable {
    private String seriesId;
    private String concertId;
    private Long startAt;
    private Long endAt;
    private Long reserveStartAt;
    private Long reserveEndAt;
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

        return ConcertSeries.builder()
                .seriesId(newId)
                .concertId(concertId)
                .startAt(startAt)
                .endAt(endAt)
                .reserveStartAt(reserveStartAt)
                .reserveEndAt(reserveEndAt)
                .createAt(now)
                .build();
    }

    public boolean  isReservationAvailable () {
        long now = System.currentTimeMillis();
        return now >= reserveStartAt && now < reserveEndAt;
    }

    public void validateReservationAvailable() {
        if(!this.isReservationAvailable())
            throw new CustomGlobalException(ErrorType.BOOKING_NOT_AVAILABLE);
    }
}
