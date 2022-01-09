package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "ink_usage")
@Entity
public class InkUsageEntity extends BaseEntity {
    private Double cyanLitresUsed;
    private Double magentaLitresUsed;
    private Double yellowLitresUsed;
    private Double blackLitresUsed;
}
