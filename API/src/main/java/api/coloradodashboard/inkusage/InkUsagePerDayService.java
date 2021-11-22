package api.coloradodashboard.inkusage;

import api.coloradodashboard.interfaces.GenericRepository;
import api.coloradodashboard.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for InkUsagePerDay model.
 */
@Service
@AllArgsConstructor
public class InkUsagePerDayService implements GenericService<InkUsagePerDay> {
    private GenericRepository<InkUsagePerDay> repository;

    /**
     * Retrieves all InkUsagePerDay model objects.
     *
     * @return
     */
    public List<InkUsagePerDay> getAll() {
        return repository.getAll();
    }
}
