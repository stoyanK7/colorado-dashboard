package api.coloradodashboard.topmachineswithmostprintvolume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "top_machines_with_most_print_volume")
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
