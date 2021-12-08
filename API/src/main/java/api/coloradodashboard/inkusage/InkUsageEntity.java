package api.coloradodashboard.inkusage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "ink_usage")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InkUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String printerId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Double cyanUsed;
    private Double magentaUsed;
    private Double yellowUsed;
    private Double blackUsed;
}
