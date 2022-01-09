package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Table(name = "media_types_per_machine")
@Entity
public class MediaTypesPerMachineEntity extends BaseEntity {
    public MediaTypesPerMachineEntity(Long id, String printerId, Date date,
                                      String mediaType, Double printedSquareMeters) {
        super(id, printerId, date);
        this.mediaType = mediaType;
        this.printedSquareMeters = printedSquareMeters;
    }

    private String mediaType;
    private Double printedSquareMeters;
}
