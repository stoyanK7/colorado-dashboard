package api.coloradodashboard.mediacategoryusage;


import api.coloradodashboard.interfaces.GenericService;
import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents Service for TotalPrintSquareMeterPerMediaCategoryPerDay model.
 */
@Service
@AllArgsConstructor
public class MediaCategoryUsagePerDayService implements
        GenericService<MediaCategoryUsagePerDay> {

    private GenericRepository<MediaCategoryUsagePerDay> repository;

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay model objects
     * stored in repository
     *
     * @return
     */
    public List<MediaCategoryUsagePerDay> getAll() {
        return repository.getAll();
    }
}
