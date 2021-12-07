package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.PeriodDto;
import api.coloradodashboard.PrinterIdsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service for <b>Top machines with most print volume</b>. Returns <b>lists of
 * TopMachinesWithMostPrintVolumeDto</b> objects depending on provided criteria.
 */
@Service
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeService {
    private TopMachinesWithMostPrintVolumeRepository repository;

    /**
     * Retrieve all data from database for all printers and all time.
     *
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<TopMachinesWithMostPrintVolumeDto> getAll() {
        return repository.getAll();
    }

    /**
     * Retrieve all data from database for a provided period of interest.
     *
     * @param from Period of interest starting date inclusive.
     * @param to   Period of interest ending date inclusive.
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<TopMachinesWithMostPrintVolumeDto> getAllForPeriod(Date from, Date to) {
        return repository.getAllForPeriod(from, to);
    }

    /**
     * Retrieve all data from database for provided list of printers.
     *
     * @param printerIds List of printer id's.
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<TopMachinesWithMostPrintVolumeDto> getPrinters(List<String> printerIds) {
        return repository.getPrinters(printerIds);
    }

    /**
     * Retrieve all data from database for a provided period of interest and list
     * of printers.
     *
     * @param from       Period of interest starting date inclusive.
     * @param to         Period of interest ending date inclusive.
     * @param printerIds List of printer id's.
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<TopMachinesWithMostPrintVolumeDto> getPrintersForPeriod(Date from, Date to, List<String> printerIds) {
        return repository.getPrintersForPeriod(from, to, printerIds);
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
