package com.hhplusconcert.infra.reservation.orm.jpo;

import com.hhplusconcert.domain.reservation.model.Reservation;
import com.hhplusconcert.infra.common.JpoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reservation")
public class ReservationJpo implements JpoEntity<Reservation> {
    @Id
    private String reservationId;
    private String userId;
    private String paymentId;
    // 예약 정보에 저장해야하는 데이터들 관리
    private String concertId;
    private String title;
    private String seriesId;
    private String seatId;
    private int seatRow;
    private int seatCol;
    private int price;

    private long createAt;

    public ReservationJpo(Reservation reservation) {
        BeanUtils.copyProperties(reservation, this);
    }

    @Override
    public Reservation toDomain() {
        return Reservation.builder()
                .reservationId(reservationId)
                .userId(userId)
                .paymentId(paymentId)
                .concertId(concertId)
                .title(title)
                .seriesId(seriesId)
                .seatId(seatId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .price(price)
                .createAt(createAt)
                .build();
    }
}
