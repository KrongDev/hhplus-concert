package com.hhplusconcert.application.concert.dto.query;

public record ConcertListQuery(
        int page,
        int size
) {
    public static ConcertListQuery of(int page, int size) {
        return new ConcertListQuery(page, size);
    }
}
