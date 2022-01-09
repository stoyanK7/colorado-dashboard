package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "square_meters_per_print_mode")
@Entity
public class SquareMetersPerPrintModeEntity extends BaseEntity {
    private String printMode;
    private Double printedSquareMeters;
}
