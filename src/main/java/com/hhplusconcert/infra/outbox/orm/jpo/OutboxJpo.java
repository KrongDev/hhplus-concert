package com.hhplusconcert.infra.outbox.orm.jpo;

import com.hhplusconcert.domain.outbox.domain.Outbox;
import com.hhplusconcert.infra.common.JpoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Getter
@Entity
@Table(name = "outbox")
@AllArgsConstructor
@NoArgsConstructor
public class OutboxJpo implements JpoEntity<Outbox> {
    @Id
    private String id;
    private String payload;
    private boolean published;
    private boolean skipped;
    private long publishedAt;
    private long createdAt;

    @Override
    public Outbox toDomain() {
        return Outbox.builder()
                .id(id)
                .payload(payload)
                .published(published)
                .skipped(skipped)
                .publishedAt(publishedAt)
                .createdAt(createdAt)
                .build();
    }

    public static OutboxJpo fromDomain(Outbox outbox) {
        OutboxJpo jpo = new OutboxJpo();
        BeanUtils.copyProperties(outbox, jpo);
        return jpo;
    }
}
