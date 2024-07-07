package com.hhplusconcert.infra.concert.orm.jpo;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.infra.common.JpoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="concert_seat")
public class ConcertSeatJpo implements JpoEntity<ConcertSeat> {
    @EmbeddedId
    private SeatKey seatId;
    @Version
    private int entityVersion;
    private int seatIndex;
    private int price;
    private boolean reserved;

    public ConcertSeatJpo(ConcertSeat concertSeat) {
        BeanUtils.copyProperties(concertSeat, this);
        this.seatId = new SeatKey(
                concertSeat.getSeriesId(),
                concertSeat.getSeatRow(),
                concertSeat.getSeatCol()
        );
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class SeatKey implements Serializable {
        private String seriesId;
        private int seatRow;
        private int seatCol;
    }

    @Override
    public ConcertSeat toDomain() {
        return ConcertSeat.builder()
                .seriesId(this.seatId.seriesId)
                .seatRow(this.seatId.seatRow)
                .seatCol(this.seatId.seatCol)
                .entityVersion(this.entityVersion)
                .seatIndex(this.seatIndex)
                .price(this.price)
                .reserved(this.reserved)
                .build();
    }
}
