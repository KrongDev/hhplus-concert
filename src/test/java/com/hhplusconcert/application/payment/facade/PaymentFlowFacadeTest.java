package com.hhplusconcert.application.payment.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
class PaymentFlowFacadeTest {
    //
    @Autowired
    private PaymentFlowFacade paymentFlowFacade;


    @Test
    void processTemporaryReservationPayment() {
        //
        String userId = "test_user_id";
        String title = "테스트 콘서트";
        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        Long end = calendar.getTimeInMillis();
        int maxRow = 5;
        int maxSeat = 50;


        //GIVEN
        //1. 콘서트 생성
        //2. 콘서트 시리즈 + 자리 생성
        //3. 콘서트 임시예약

        //WHEN
        //1. 임시예약 좌석 결제

        //THEN
        //1. 예약 좌석 정상 등록 검증




    }
}
