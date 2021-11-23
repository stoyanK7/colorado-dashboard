package api.coloradodashboard.interfaces;

import api.coloradodashboard.mediacategoryusage.MediaCategoryUsagePerDay;

import java.util.Date;
import java.util.List;

public interface IFiltrationService {
    List<MediaCategoryUsagePerDay> getDataByDates(Date startingDate, Date endingDate);
}
