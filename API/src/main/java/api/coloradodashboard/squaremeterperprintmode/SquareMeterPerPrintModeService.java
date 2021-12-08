package api.coloradodashboard.squaremeterperprintmode;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for SquareMeterPerPrintModePerDay model.
 */
@Service
@AllArgsConstructor
public class SquareMeterPerPrintModeService {
    private SquareMeterPerPrintModeRepository repository;

    /**
     * Retrieves all SquareMeterPerPrintModePerDay model objects.
     *
     * @return
     */
    public List<SquareMeterPerPrintMode> getAll() {
        return repository.getAllByOrderByDateAsc();
    }
}
