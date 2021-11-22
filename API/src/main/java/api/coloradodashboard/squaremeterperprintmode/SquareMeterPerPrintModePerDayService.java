package api.coloradodashboard.squaremeterperprintmode;

import api.coloradodashboard.interfaces.GenericRepository;
import api.coloradodashboard.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for SquareMeterPerPrintModePerDay model.
 */
@Service
@AllArgsConstructor
public class SquareMeterPerPrintModePerDayService implements GenericService<SquareMeterPerPrintModePerDay> {
    private GenericRepository<SquareMeterPerPrintModePerDay> repository;

    /**
     * Retrieves all SquareMeterPerPrintModePerDay model objects.
     *
     * @return
     */
    public List<SquareMeterPerPrintModePerDay> getAll() {
        return repository.getAll();
    }
}
