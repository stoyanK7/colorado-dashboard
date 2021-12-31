package api.coloradodashboard.mediatypespermachine;

import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.dto.PrinterIdsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for <b>Media types per machine</b>. Returns <b>lists of
 * TopMachinesWithMostPrintVolumeDto</b> objects depending on provided criteria.
 */
@Service
@AllArgsConstructor
public class MediaTypesPerMachineService {
    private MediaTypesPerMachineRepository repository;

    public List<MediaTypesPerMachineDto> getAll() {
        return repository.getAll();
    }

    public List<MediaTypesPerMachineDto> getAllForPeriod(Date from, Date to) {
        return repository.getAllForPeriod(from, to);
    }

    public List<MediaTypesPerMachineDto> getPrinters(List<String> printerIds) {
        return new ArrayList<MediaTypesPerMachineDto>();

    }

    public List<MediaTypesPerMachineDto> getPrintersForPeriod(Date from, Date to, List<String> printerIds) {
        return new ArrayList<MediaTypesPerMachineDto>();

    }

    /**
     * Retrieve minimum and maximum possible dates.
     *
     * @return A <b>PeriodDto object</b> containing the minimum and maximum possible
     * dates.
     */
    public PeriodDto getAvailableTimePeriod() {
        return repository.getAvailableTimePeriod();
    }

    /**
     * Retrieve all available printers in the database table.
     *
     * @return A <b>PrinterIdsDto object</b>, containing a list of all available
     * printers.
     */
    public PrinterIdsDto getAvailablePrinters() {
        return new PrinterIdsDto(repository.getAvailablePrinters());
    }
}
