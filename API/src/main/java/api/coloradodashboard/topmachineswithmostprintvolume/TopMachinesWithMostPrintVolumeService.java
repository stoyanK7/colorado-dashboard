package api.coloradodashboard.topmachineswithmostprintvolume;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for
 */
@Service
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeService {
    private TopMachinesWithMostPrintVolumeRepository repository;

    public List<TopMachinesWithMostPrintVolumeDto> getAll() {
        return repository.getAll();
    }

    public List<TopMachinesWithMostPrintVolumeDto> getAllForPeriod(Date from, Date to) {
        return repository.getAllForPeriod(from, to);
    }

    public List<TopMachinesWithMostPrintVolumeDto> getPrinters(List<String> printerIds) {
        return repository.getPrinters(printerIds);
    }

    public List<TopMachinesWithMostPrintVolumeDto> getPrintersForPeriod
            (Date from, Date to, List<String> printerIds) {
        return new ArrayList<TopMachinesWithMostPrintVolumeDto>();

    }
}
