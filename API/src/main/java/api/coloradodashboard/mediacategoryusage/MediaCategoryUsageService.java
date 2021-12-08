package api.coloradodashboard.mediacategoryusage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents Service for MediaCategoryUsage model.
 */
@Service
@AllArgsConstructor
public class MediaCategoryUsageService{
    private MediaCategoryUsageRepository repository;

    /**
     * Retrieves all MediaCategoryUsage model objects.
     *
     * @return
     */
    public List<MediaCategoryUsage> getAll() {
        return repository.getAllByOrderByDateAsc();
    }
}
