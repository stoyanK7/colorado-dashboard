package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Table(name = "square_meters_per_print_mode")
@Entity
public class SquareMetersPerPrintModeEntity extends BaseEntity {
    public SquareMetersPerPrintModeEntity(Long id, String printerId, Date date,
                                          String printMode, Double printedSquareMeters) {
        super(id, printerId, date);
        this.printMode = printMode;
        this.printedSquareMeters = printedSquareMeters;
    }

    private String printMode;
    private Double printedSquareMeters;
}
