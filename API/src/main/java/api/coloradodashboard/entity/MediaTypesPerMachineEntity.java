package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "media_types_per_machine")
@Entity
public class MediaTypesPerMachineEntity extends BaseEntity {
    private String mediaType;
    private Double printedSquareMeters;
}
