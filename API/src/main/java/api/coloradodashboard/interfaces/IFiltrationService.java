package api.coloradodashboard.interfaces;

import api.coloradodashboard.mediacategoryusage.MediaCategoryUsage;

import java.util.Date;
import java.util.List;

public interface IFiltrationService {
    List<MediaCategoryUsage> getDataByDates(Date startingDate, Date endingDate);
}
