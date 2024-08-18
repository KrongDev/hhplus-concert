package com.hhplusconcert.domain.payment.event;

import com.hhplusconcert.domain.common.Event;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentConfirmed extends Event {
    public static final String topicId = "payment_confirmed";
        private String temporaryReservationId;
        private String userId;
        private String concertId;
        private String title;
        private String seriesId;
        private String seatId;
        private int seatRow;
        private int seatCol;
        private int price;
        private String paymentId;

    public static PaymentConfirmed of(
            String temporaryReservationId,
            String userId,
            String concertId,
            String title,
            String seriesId,
            String seatId,
            int seatRow,
            int seatCol,
            int price,
            String paymentId
    ) {
        return PaymentConfirmed.builder()
                .temporaryReservationId(temporaryReservationId)
                .userId(userId)
                .concertId(concertId)
                .title(title)
                .seriesId(seriesId)
                .seatId(seatId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .price(price)
                .paymentId(paymentId)
                .build();
    }
}
