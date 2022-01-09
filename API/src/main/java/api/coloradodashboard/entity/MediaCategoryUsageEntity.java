package api.coloradodashboard.entity;

import api.coloradodashboard.entity.base.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "media_category_usage")
@Entity
public class MediaCategoryUsageEntity extends BaseEntity {
    private String mediaCategory;
    private Double printedSquareMeters;
}
