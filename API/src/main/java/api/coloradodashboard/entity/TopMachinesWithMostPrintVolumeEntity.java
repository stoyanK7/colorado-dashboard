package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "top_machines_with_most_print_volume")
@Entity
public class TopMachinesWithMostPrintVolumeEntity extends BaseEntity {
    private Double printedSquareMeters;
}
