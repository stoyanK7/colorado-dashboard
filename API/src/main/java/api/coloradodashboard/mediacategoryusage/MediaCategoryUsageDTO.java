package api.coloradodashboard.mediacategoryusage;

import lombok.Data;

import java.util.Date;

@Data
public class MediaCategoryUsageDTO {
    private Date dayDate;
    private double Canvas;
    private double Film;
    private double HeavyBanner;
    private double Paper;
    private double Textile;
}
