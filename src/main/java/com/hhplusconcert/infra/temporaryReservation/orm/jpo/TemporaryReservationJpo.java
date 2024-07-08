package com.hhplusconcert.infra.temporaryReservation.orm.jpo;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
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
@Table(name = "temporary_reservation")
public class TemporaryReservationJpo implements JpoEntity<TemporaryReservation> {
    @Id
    private String temporaryReservationId;
    private String userId;
    private String concertId;
    private String seriesId;
    private int seatRow;
    private int seatCol;
    private Long createAt;
    private Long deleteAt;

    public TemporaryReservationJpo(TemporaryReservation temporaryReservation) {
        //
        BeanUtils.copyProperties(temporaryReservation, this);
    }

    @Override
    public TemporaryReservation toDomain() {
        return TemporaryReservation.builder()
                .temporaryReservationId(temporaryReservationId)
                .userId(userId)
                .concertId(concertId)
                .seriesId(seriesId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .createAt(createAt)
                .deleteAt(deleteAt)
                .build();
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
