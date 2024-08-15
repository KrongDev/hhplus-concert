package com.hhplusconcert.application.concert.facade;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
class ConcertFlowFacadeTest {

    @Autowired
    private ConcertFlowFacade concertFlowFacade;

    @Test
    @DisplayName("콘서트 시리즈 생성시 콘서트 없는 경우-CONCERT_NOT_FOUND 에러 발생")
    void createConcertSeries() {
        //GIVEN
        String concertId = "test_concertId";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        long startAt = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, -1);
        long endAt = calendar.getTimeInMillis();

        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> concertFlowFacade.createConcertSeries(
                concertId,
                startAt,
                endAt,
                startAt,
                endAt,
                5,
                50
        ));
        //THEN
        assertEquals(ErrorType.CONCERT_NOT_FOUND, exception.getErrorType());
    }
}
