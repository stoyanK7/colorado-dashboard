package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Table(name = "top_machines_with_most_print_volume")
@Entity
public class TopMachinesWithMostPrintVolumeEntity extends BaseEntity {
    public TopMachinesWithMostPrintVolumeEntity(Long id, String printerId, Date date,
                                                Double printedSquareMeters) {
        super(id, printerId, date);
        this.printedSquareMeters = printedSquareMeters;
    }

    private Double printedSquareMeters;
}
