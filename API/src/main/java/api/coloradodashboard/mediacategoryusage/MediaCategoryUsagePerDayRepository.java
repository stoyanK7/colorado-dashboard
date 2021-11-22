package api.coloradodashboard.mediacategoryusage;

import api.coloradodashboard.interfaces.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for TotalPrintSquareMeterPerMediaCategoryPerDay model connected
 * to JPA database
 */
@Repository("WorkRepo")
public class MediaCategoryUsagePerDayRepository implements
        GenericRepository<MediaCategoryUsagePerDay> {
    @Autowired
    private JpaMediaCategoryUsagePerDayRepository jpa;

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay models from the
     * database
     * @return
     */
    @Override
    public List<MediaCategoryUsagePerDay> getAll() {
        return jpa.findAll();
    }
}
