package api.coloradodashboard.entity;

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
@Table(name = "square_meter_per_print_mode")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SquareMetersPerPrintModeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String printerId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Double maxSpeedPrinted;
    private Double highSpeedPrinted;
    private Double productionPrinted;
    private Double highQualityPrinted;
    private Double specialtyPrinted;
    private Double backlitPrinted;
    private Double reliancePrinted;
    private Double otherPrinted;
}
