package com.hhplusconcert.application.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfo {
    //
    private String reservationId;
    private String userId;
    private String concertId;
    private String seriesId;
    private String paymentId;
    private String seatId;

    /**
     * 추가 데이터들 - 이때 반정규화 데이터 확인
     */

    private LocalDateTime createAt;

    public static ReservationInfo sample() {
        return new ReservationInfo(
            "",
            "",
            "",
            "",
            "",
            "",
                LocalDateTime.now()
        );
    }
}
