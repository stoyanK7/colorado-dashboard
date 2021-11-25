package api.coloradodashboard.mediacategoryusage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents data of the total number of square meters of a specific media
 * category that was printed on a single day.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MediaCategoryUsage {
    @Id
    private long machineId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private double printedSquareMeters;
    @Enumerated(EnumType.STRING)
    private MediaCategory mediaCategory;
}
