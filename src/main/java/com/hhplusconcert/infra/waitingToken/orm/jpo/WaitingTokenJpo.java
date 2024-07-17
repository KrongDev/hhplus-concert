package com.hhplusconcert.infra.waitingToken.orm.jpo;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;
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
@Table(name="waiting_token")
public class WaitingTokenJpo implements JpoEntity<WaitingToken> {
    //
    @Id
    private String tokenId;
    private String userId;
    private String seriesId;
    private Long createAt;
    private Long expiredAt; // 토큰 만료 -> 대기열에 해당 토큰 존재여부 확인( 여기서 풀스캔 ) -> 대기열 상태 변경

    public WaitingTokenJpo (WaitingToken waitingToken) {
        //
        BeanUtils.copyProperties(waitingToken, this);
    }

    @Override
    public WaitingToken toDomain() {
        return WaitingToken.builder()
                .tokenId(tokenId)
                .userId(userId)
                .seriesId(seriesId)
                .createAt(createAt)
                .expiredAt(expiredAt)
                .build();
    }
}
