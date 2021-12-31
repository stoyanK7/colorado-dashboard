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
@Table(name = "media_types_per_machine")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MediaTypesPerMachineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String printerId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Double printedSquareMeters;
    private String mediaType;
}
