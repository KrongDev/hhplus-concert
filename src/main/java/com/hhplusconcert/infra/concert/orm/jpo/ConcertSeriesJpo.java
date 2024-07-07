package com.hhplusconcert.infra.concert.orm.jpo;

import com.hhplusconcert.domain.concert.model.ConcertSeries;
import com.hhplusconcert.domain.concert.model.vo.ConcertSeriesStatus;
import com.hhplusconcert.infra.common.JpoEntity;
import jakarta.persistence.*;
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
@Table(name="concert_series")
public class ConcertSeriesJpo implements JpoEntity<ConcertSeries> {
    @Id
    private String seriesId;
    private String concertId;
    private Long startAt;
    private Long endAt;
    private Long reserveStartAt;
    private Long reserveEndAt;
    @Enumerated(EnumType.STRING)
    private ConcertSeriesStatus status;

    private Long createAt;

    public ConcertSeriesJpo(ConcertSeries concertSeries) {
        BeanUtils.copyProperties(concertSeries, this);
    }

    @Override
    public ConcertSeries toDomain() {
        ConcertSeries concertSeries = new ConcertSeries();
        BeanUtils.copyProperties(this, concertSeries);
        return ConcertSeries.builder()
                .seriesId(seriesId)
                .concertId(concertId)
                .startAt(startAt)
                .endAt(endAt)
                .reserveStartAt(reserveStartAt)
                .reserveEndAt(reserveEndAt)
                .status(status)
                .createAt(createAt)
                .build();
    }
}
