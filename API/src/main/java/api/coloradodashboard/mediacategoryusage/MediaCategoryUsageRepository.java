package api.coloradodashboard.mediacategoryusage;

import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for MediaCategoryUsage model.
 */
@Repository
@AllArgsConstructor
public class MediaCategoryUsageRepository
        implements GenericRepository<MediaCategoryUsage> {
    private JpaMediaCategoryUsageRepository jpa;

    /**
     * Gets all MediaCategoryUsage models from the database.
     *
     * @return
     */
    @Override
    public List<MediaCategoryUsage> getAll() {
        return jpa.findAll();
    }
}
