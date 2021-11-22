package api.coloradodashboard.filter;

import api.coloradodashboard.mediacategoryusage.MediaCategoryUsagePerDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FiltrationService implements api.coloradodashboard.interfaces.FiltrationService {

    @Autowired
    private IFilterRepository repo;

    /**
     * Retrieves data for the timeframe
     * @param startingDate
     * @param endingDate
     * @return
     */
    @Override
    public List<MediaCategoryUsagePerDay> getDataByDates(Date startingDate, Date endingDate) {
        return repo.getDataByDates(startingDate, endingDate);
    }
}
