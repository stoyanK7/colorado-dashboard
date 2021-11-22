package api.coloradodashboard.squaremeterperprintmode;

import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SquareMeterPerPrintModePerDay model.
 */
@Repository
@AllArgsConstructor
public class SquareMeterPerPrintModePerDayRepository implements GenericRepository<SquareMeterPerPrintModePerDay> {
    private JpaSquareMeterPerPrintModePerDayRepository jpa;

    /**
     * Retrieves all SquareMeterPerPrintModePerDay models from the database.
     *
     * @return
     */
    @Override
    public List<SquareMeterPerPrintModePerDay> getAll() {
        return jpa.findAll();
    }
}
