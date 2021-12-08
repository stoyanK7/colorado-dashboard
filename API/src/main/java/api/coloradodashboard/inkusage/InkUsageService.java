package api.coloradodashboard.inkusage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for InkUsage model.
 */
@Service
@AllArgsConstructor
public class InkUsageService {
    private InkUsageRepository repository;

    /**
     * Retrieves all InkUsage model objects.
     *
     * @return
     */
    public List<InkUsage> getAll() {
        return repository.getAllByOrderByDateAsc();
    }
}
