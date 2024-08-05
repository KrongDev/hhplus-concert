package com.hhplusconcert.domain.watingToken.model;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class WaitingTokenTest {

    @Test
    @DisplayName("토큰 만료된 경우-TOKEN_EXPIRED 에러 발생")
    public void validateExpired() {
        // GIVEN
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        WaitingToken token = WaitingToken.builder()
                .expiredAt(calendar.getTimeInMillis())
                .build();
        // WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, token::validateExpired);
        // THEN
        assertEquals(ErrorType.TOKEN_EXPIRED, exception.getErrorType());
    }
}
