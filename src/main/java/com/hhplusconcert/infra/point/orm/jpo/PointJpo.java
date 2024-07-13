package com.hhplusconcert.infra.point.orm.jpo;

import com.hhplusconcert.domain.point.model.Point;
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
@Table(name = "point")
public class PointJpo implements JpoEntity<Point> {
    @Id
    private String userId;
    @Version
    private int entityVersion;
    private int point;

    public PointJpo(Point point) {
        BeanUtils.copyProperties(point, this);
    }

    @Override
    public Point toDomain() {
        return Point.builder()
                .userId(userId)
                .entityVersion(entityVersion)
                .point(point)
                .build();
    }
}
