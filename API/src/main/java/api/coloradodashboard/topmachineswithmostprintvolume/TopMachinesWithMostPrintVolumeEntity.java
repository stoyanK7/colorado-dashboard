package api.coloradodashboard.topmachineswithmostprintvolume;

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
public class TopMachinesWithMostPrintVolumeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String printerId;
    private Date date;
    private Double printedSquareMeters;
}
