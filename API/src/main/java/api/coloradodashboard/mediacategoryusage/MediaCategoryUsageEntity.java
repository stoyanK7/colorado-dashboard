package api.coloradodashboard.mediacategoryusage;

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
@Table(name = "media_category_usage")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MediaCategoryUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String printerId;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Double filmUsed;
    private Double lightPaperUsed;
    private Double heavyPaperUsed;
    private Double lightBannerUsed;
    private Double textileUsed;
    private Double monomericVinylUsed;
    private Double canvasUsed;
    private Double polymericAndCastVinylUsed;
    private Double heavyBannerUsed;
    private Double paperUsed;
    private Double thickFilmUsed;
}
