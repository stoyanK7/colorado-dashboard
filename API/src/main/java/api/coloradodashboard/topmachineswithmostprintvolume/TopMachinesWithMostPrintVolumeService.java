package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintMode;
import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for SquareMeterPerPrintModePerDay model.
 */
@Service
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeService {
    private SquareMeterPerPrintModeRepository repository;

    /**
     * Retrieves top 10 SquareMeterPerPrintMode models from the database ordered
     * descending by the amount of printed square meters.
     *
     * @return
     */
//    public List<SquareMeterPerPrintMode> getTop10() {
//        return repository.getTop10();
//    }
}
