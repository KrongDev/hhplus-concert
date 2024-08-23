package com.hhplusconcert.application.waitingQueue.dto;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;

public record WaitingDto (
        String tokenId,
        long count,
        String userId,
        String seriesId,
        long createAt,
        long expiredAt
){

    public static WaitingDto from(WaitingToken token) {
        //
        return new WaitingDto(
                token.getTokenId(),
                0,
                token.getUserId(),
                token.getSeriesId(),
                token.getCreateAt(),
                token.getExpiredAt()
        );
    }

    public static WaitingDto from(Long count) {
        //
        return new WaitingDto(
                "",
                count,
                "",
                "",
                0,
                0
        );
    }
}
