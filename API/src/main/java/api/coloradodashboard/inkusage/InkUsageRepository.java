package api.coloradodashboard.inkusage;

import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for InkUsage model.
 */
@Repository
@AllArgsConstructor
public class InkUsageRepository implements GenericRepository<InkUsage> {
    private JpaInkUsageRepository jpa;

    /**
     * Retrieves all InkUsage models from the database.
     *
     * @return
     */
    @Override
    public List<InkUsage> getAll() {
        return jpa.findAll();
    }
}
