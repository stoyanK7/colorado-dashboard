package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Table(name = "media_category_usage")
@Entity
public class MediaCategoryUsageEntity extends BaseEntity {
    public MediaCategoryUsageEntity(Long id, String printerId, Date date,
                                    String mediaCategory, Double printedSquareMeters) {
        super(id, printerId, date);
        this.mediaCategory = mediaCategory;
        this.printedSquareMeters = printedSquareMeters;
    }

    private String mediaCategory;
    private Double printedSquareMeters;
}
