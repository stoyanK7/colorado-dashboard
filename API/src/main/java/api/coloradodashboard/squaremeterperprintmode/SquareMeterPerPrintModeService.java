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
public class SquareMeterPerPrintModeService
        implements GenericService<SquareMeterPerPrintMode> {
    private GenericRepository<SquareMeterPerPrintMode> repository;

    /**
     * Retrieves all SquareMeterPerPrintModePerDay model objects.
     *
     * @return
     */
    public List<SquareMeterPerPrintMode> getAll() {
        return repository.getAll();
    }
}
