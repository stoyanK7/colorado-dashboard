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
public class TotalPrintSquareMeterPerMediaCategoryPerDayService implements
        GenericService<TotalPrintSquareMeterPerMediaCategoryPerDay> {

    private GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> repository;

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay model objects
     * stored in repository
     *
     * @return
     */
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return repository.getAll();
    }
}
