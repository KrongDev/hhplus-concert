package com.hhplusconcert.infra.point.orm.jpo;

import com.hhplusconcert.domain.point.model.PointHistory;
import com.hhplusconcert.domain.point.model.vo.PointHistoryStatus;
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
@Table(name = "point_history")
public class PointHistoryJpo implements JpoEntity<PointHistory> {
    @Id
    private String pointHistoryId;
    private String paymentId;
    private String userId;
    private int amount;
    private PointHistoryStatus status;
    private long createAt;

    public PointHistoryJpo(PointHistory pointHistory) {
        BeanUtils.copyProperties(pointHistory, this);
    }

    @Override
    public PointHistory toDomain() {
        return PointHistory.builder()
                .pointHistoryId(pointHistoryId)
                .paymentId(paymentId)
                .userId(userId)
                .amount(amount)
                .status(status)
                .createAt(createAt)
                .build();
    }
}
