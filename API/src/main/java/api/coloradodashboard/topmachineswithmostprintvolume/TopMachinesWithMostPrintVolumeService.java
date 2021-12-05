package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for SquareMeterPerPrintModePerDay model.
 */
@Service
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeService {
    private SquareMeterPerPrintModeRepository repository;

    public List<TopMachinesWithMostPrintVolumeDto> getAll() {
        return new ArrayList<TopMachinesWithMostPrintVolumeDto>();
    }

    public List<TopMachinesWithMostPrintVolumeDto> getAllForPeriod(Date from, Date to) {
        return new ArrayList<TopMachinesWithMostPrintVolumeDto>();

    }

    public List<TopMachinesWithMostPrintVolumeDto> getPrinters(List<String> printerIds) {
        return new ArrayList<TopMachinesWithMostPrintVolumeDto>();

    }

    public List<TopMachinesWithMostPrintVolumeDto> getPrintersForPeriod(Date from, Date to, List<String> printerIds) {
        return new ArrayList<TopMachinesWithMostPrintVolumeDto>();

    }
}
