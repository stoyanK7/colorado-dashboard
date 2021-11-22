package api.coloradodashboard.filter;

import api.coloradodashboard.mediacategoryusage.MediaCategoryUsagePerDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class JpaFiltrationRepository implements IFilterRepository {

    @Autowired
    JpaFilter jpa;

    /**
     * Retrieves data for the timeframe
     * @param startingDate
     * @param endingDate
     * @return
     */
    @Override
    public List<MediaCategoryUsagePerDay> getDataByDates(Date startingDate, Date endingDate) {
        return jpa.findAllByDateTimeBetween(startingDate, endingDate);
    }
}
