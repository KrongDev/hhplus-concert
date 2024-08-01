package com.hhplusconcert.domain.concert.model;

import lombok.*;


import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Concert implements Serializable {
    //
    private String concertId;
    private String title;

    private String creator;
    private Long createAt;

    public static Concert newInstance(String creator, String title) {
        String concertId = UUID.randomUUID().toString();
        Long createAt = System.currentTimeMillis();
        return Concert.builder()
                .concertId(concertId)
                .title(title)
                .creator(creator)
                .createAt(createAt)
                .build();
    }
}
