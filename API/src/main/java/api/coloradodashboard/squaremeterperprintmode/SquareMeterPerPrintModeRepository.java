package api.coloradodashboard.squaremeterperprintmode;

import api.coloradodashboard.interfaces.GenericRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SquareMeterPerPrintMode model.
 */
@Repository
@AllArgsConstructor
public class SquareMeterPerPrintModeRepository
        implements GenericRepository<SquareMeterPerPrintMode> {
    private JpaSquareMeterPerPrintModeRepository jpa;

    /**
     * Retrieves all SquareMeterPerPrintMode models from the database.
     *
     * @return
     */
    @Override
    public List<SquareMeterPerPrintMode> getAll() {
        return jpa.findAll();
    }
}
