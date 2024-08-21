package com.hhplusconcert.application.concert.dto.query;

public record ConcertSeriesListQuery(
        String concertId,
        int page,
        int size
) {
    public static ConcertSeriesListQuery of( String concertId, int page, int size) {
        return new ConcertSeriesListQuery(concertId, page, size);
    }
}
