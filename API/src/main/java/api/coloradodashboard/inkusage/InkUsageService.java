package api.coloradodashboard.inkusage;

import api.coloradodashboard.interfaces.GenericRepository;
import api.coloradodashboard.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for InkUsage model.
 */
@Service
@AllArgsConstructor
public class InkUsageService implements GenericService<InkUsage> {
    private GenericRepository<InkUsage> repository;

    /**
     * Retrieves all InkUsage model objects.
     *
     * @return
     */
    public List<InkUsage> getAll() {
        return repository.getAll();
    }
}
