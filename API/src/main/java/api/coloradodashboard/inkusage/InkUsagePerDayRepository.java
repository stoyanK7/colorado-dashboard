package api.coloradodashboard.inkusage;

import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for InkUsagePerDay model.
 */
@Repository
@AllArgsConstructor
public class InkUsagePerDayRepository implements GenericRepository<InkUsagePerDay> {
    private JpaInkUsagePerDayRepository jpa;

    /**
     * Retrieves all InkUsagePerDay models from the database.
     *
     * @return
     */
    @Override
    public List<InkUsagePerDay> getAll() {
        return jpa.findAll();
    }
}
