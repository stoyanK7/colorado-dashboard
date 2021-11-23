package api.coloradodashboard.squaremeterperprintmode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SquareMeterPerPrintMode {
    @Id
    private long machineId;
    private Date date;
    private double printedSquareMeters;
    private PrintMode printMode;
}
