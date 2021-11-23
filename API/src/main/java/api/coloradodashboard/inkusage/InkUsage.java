package api.coloradodashboard.inkusage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InkUsage {
    @Id
    private long machineId;
    private Date date;
    private double inkUsed;
    private InkType inkType;
}
