package com.hhplusconcert.domain.watingToken.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import lombok.Builder;
import lombok.Getter;

import java.util.Calendar;
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
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.MINUTE, 5);
        return WaitingToken.builder()
                .tokenId(newId)
                .userId(userId)
                .seriesId(seriesId)
                .createAt(now)
                .expiredAt(calendar.getTimeInMillis())
                .build();
    }

    public void healthCheck() {
        this.validateExpired();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        this.expiredAt = calendar.getTimeInMillis();
    }

    public void validateExpired() {
        //
        long now = System.currentTimeMillis();
        if(now > expiredAt)
            throw new CustomGlobalException(ErrorType.TOKEN_EXPIRED);
    }
}
