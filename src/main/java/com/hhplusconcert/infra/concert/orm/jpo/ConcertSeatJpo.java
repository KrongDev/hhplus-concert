package com.hhplusconcert.infra.concert.orm.jpo;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
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
@Table(name="concert_seat")
public class ConcertSeatJpo implements JpoEntity<ConcertSeat> {
    @Id
    private String seatId;
    private String seriesId;
    private int seatRow;
    private int seatCol;
    @Version
    private int entityVersion;
    private int seatIndex;
    private int price;
    private boolean reserved;

    public ConcertSeatJpo(ConcertSeat concertSeat) {
        BeanUtils.copyProperties(concertSeat, this);
    }

    @Override
    public ConcertSeat toDomain() {
        return ConcertSeat.builder()
                .seatId(seatId)
                .seriesId(seriesId)
                .seatRow(seatRow)
                .seatCol(seatCol)
                .entityVersion(this.entityVersion)
                .seatIndex(this.seatIndex)
                .price(this.price)
                .reserved(this.reserved)
                .build();
    }
}
