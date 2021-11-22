package api.coloradodashboard.filter;

import api.coloradodashboard.mediacategoryusage.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.util.Date;
import java.util.List;

public interface IFilterRepository {

    /**
     * Retrieves data for the timeframe
     * @param startingDate
     * @param endingDate
     * @return
     */
    List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(Date startingDate, Date endingDate);
}
