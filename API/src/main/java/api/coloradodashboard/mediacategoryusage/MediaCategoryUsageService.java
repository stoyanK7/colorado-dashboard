package api.coloradodashboard.mediacategoryusage;


import api.coloradodashboard.interfaces.GenericService;
import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents Service for MediaCategoryUsage model.
 */
@Service
@AllArgsConstructor
public class MediaCategoryUsageService implements
        GenericService<MediaCategoryUsage> {
    private GenericRepository<MediaCategoryUsage> repository;

    /**
     * Retrieves all MediaCategoryUsage model objects.
     *
     * @return
     */
    public List<MediaCategoryUsage> getAll() {
        return repository.getAll();
    }
}
