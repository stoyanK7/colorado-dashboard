package api.coloradodashboard.squaremeterperprintmode;

import api.coloradodashboard.PeriodDto;
import api.coloradodashboard.PrinterIdsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service for <b>Square meters per print mode</b>. Returns <b>lists of
 * SquareMeterPrintModeDto</b> objects depending on provided criteria.
 */
@Service
@AllArgsConstructor
public class SquareMeterPerPrintModeService {
    private SquareMeterPerPrintModeRepository repository;

    /**
     * Retrieve all data from database for all printers and all time.
     *
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different date. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<SquareMeterPerPrintModeDto> getAll() {
        return repository.getAll();
    }

    /**
     * Retrieve all data from database for a provided period of interest.
     *
     * @param from Period of interest starting date inclusive.
     * @param to   Period of interest ending date inclusive.
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different date. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<SquareMeterPerPrintModeDto> getAllForPeriod(Date from, Date to) {
        return repository.getAllForPeriod(from, to);
    }

    /**
     * Retrieve all data from database for provided list of printers.
     *
     * @param printerIds List of printer id's.
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different date. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<SquareMeterPerPrintModeDto> getPrinters(List<String> printerIds) {
        return repository.getPrinters(printerIds);
    }

    /**
     * Retrieve all data from database for a provided period of interest and list
     * of printers.
     *
     * @param from       Period of interest starting date inclusive.
     * @param to         Period of interest ending date inclusive.
     * @param printerIds List of printer id's.
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different date. An <b>empty list</b> if no data
     * is present in the database.
     */
    public List<SquareMeterPerPrintModeDto> getPrintersForPeriod(Date from, Date to, List<String> printerIds) {
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
