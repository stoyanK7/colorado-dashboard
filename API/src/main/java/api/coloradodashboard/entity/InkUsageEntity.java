package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Table(name = "ink_usage")
@Entity
public class InkUsageEntity extends BaseEntity {
    public InkUsageEntity(Long id, String printerId, Date date,
                          Double cyanLitresUsed, Double magentaLitresUsed,
                          Double yellowLitresUsed, Double blackLitresUsed) {
        super(id, printerId, date);
        this.cyanLitresUsed = cyanLitresUsed;
        this.magentaLitresUsed = magentaLitresUsed;
        this.yellowLitresUsed = yellowLitresUsed;
        this.blackLitresUsed = blackLitresUsed;
    }

    private Double cyanLitresUsed;
    private Double magentaLitresUsed;
    private Double yellowLitresUsed;
    private Double blackLitresUsed;
}
