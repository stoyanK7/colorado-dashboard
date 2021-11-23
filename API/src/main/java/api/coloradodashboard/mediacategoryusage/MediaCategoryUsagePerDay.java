package api.coloradodashboard.mediacategoryusage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Represents data of the total number of square meters of a specific media
 * category that was printed on a single day.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MediaCategoryUsagePerDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date date;
    private double totalPrintedSquareMeter;
    private MediaCategory mediaCategory;
}
