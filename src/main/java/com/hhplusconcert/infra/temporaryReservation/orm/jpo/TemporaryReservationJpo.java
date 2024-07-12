package com.hhplusconcert.infra.temporaryReservation.orm.jpo;

import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.infra.common.JpoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
    @Version
    private int entityVersion;
    private String userId;
    private String concertId;
    private String title;
    private String seriesId;
    private String seatId;
    private int seatRow;
    private int seatCol;
    private int price;
    private Long createAt;
    private Long deleteAt;
    private boolean paid;

    public TemporaryReservationJpo(TemporaryReservation temporaryReservation) {
        //
        BeanUtils.copyProperties(temporaryReservation, this);
    }

    @Override
    public TemporaryReservation toDomain() {
        return TemporaryReservation.builder()
                .temporaryReservationId(temporaryReservationId)
                .entityVersion(entityVersion)
                .userId(userId)
                .concertId(concertId)
                .title(title)
                .seriesId(seriesId)
                .seatId(seatId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .price(price)
                .createAt(createAt)
                .deleteAt(deleteAt)
                .paid(paid)
                .build();
    }
}
