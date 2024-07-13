package com.hhplusconcert.infra.concert.orm.jpo;

import com.hhplusconcert.domain.concert.model.Concert;
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
@Table(name="concert")
public class ConcertJpo implements JpoEntity<Concert> {
    @Id
    private String concertId;
    private String title;

    private String creator;
    private Long createAt;

    public ConcertJpo(Concert domain) {
        //
        BeanUtils.copyProperties(domain, this);
    }

    @Override
    public Concert toDomain() {
        return Concert.builder()
                .concertId(this.concertId)
                .title(this.title)
                .creator(this.creator)
                .createAt(this.createAt)
                .build();
    }
}
