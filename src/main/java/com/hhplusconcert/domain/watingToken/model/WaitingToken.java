package com.hhplusconcert.domain.watingToken.model;

import com.hhplusconcert.domain.common.exception.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.ErrorType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class WaitingToken {
    private String tokenId;
    private String userId;
    private String seriesId;
    private Long createAt;
    private Long expiredAt;

    public static WaitingToken newInstance(String userId, String seriesId) {
        //
        String newId = UUID.randomUUID().toString();
        return WaitingToken.builder()
                .tokenId(newId)
                .userId(userId)
                .seriesId(seriesId)
                .createAt(System.currentTimeMillis())
                .build();
    }

    public void validateExpired() {
        //
        long now = System.currentTimeMillis();
        if(now > expiredAt)
            throw new CustomGlobalException(ErrorType.TOKEN_EXPIRED);
    }
}
