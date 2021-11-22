package api.coloradodashboard.interfaces;

import api.coloradodashboard.mediacategoryusage.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.util.Date;
import java.util.List;

public interface FiltrationService {
    List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(Date startingDate, Date endingDate);
}
