package api.coloradodashboard.squaremeterperprintmode;

import api.coloradodashboard.dto.PeriodDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Square meters per
 * print mode</b>.
 */
public interface SquareMeterPerPrintModeRepository extends JpaRepository<SquareMeterPerPrintModeEntity, Long> {
    /**
     * Retrieve aggregated printed square meters for all print modes for all printers for all time
     * grouping by printer, ordered by printed square meters descending.
     *
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeDto(s.date, sum(s.maxSpeedPrinted), sum(s.highSpeedPrinted), sum(s.productionPrinted), sum(s.highQualityPrinted), sum(s.specialtyPrinted), sum(s.backlitPrinted), sum(s.reliancePrinted), sum(s.otherPrinted)) " +
            "FROM SquareMeterPerPrintModeEntity s " +
            "GROUP BY s.date " +
            "ORDER BY s.date ASC")
    List<SquareMeterPerPrintModeDto> getAll();

    /**
     * Retrieve aggregated printed square meters for all print modes for all printers for provided
     * period of interest, ordered by printed square meters descending.
     *
     * @param from Period of interest starting date inclusive.
     * @param to   Period of interest ending date inclusive.
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeDto(s.date, sum(s.maxSpeedPrinted), sum(s.highSpeedPrinted), sum(s.productionPrinted), sum(s.highQualityPrinted), sum(s.specialtyPrinted), sum(s.backlitPrinted), sum(s.reliancePrinted), sum(s.otherPrinted)) " +
            "FROM SquareMeterPerPrintModeEntity s " +
            "WHERE s.date BETWEEN :from AND :to " +
            "GROUP BY s.date " +
            "ORDER BY s.date ASC")
    List<SquareMeterPerPrintModeDto> getAllForPeriod(@Param("from") Date from, @Param("to") Date to);

    /**
     * Retrieve aggregated printed square meters for all time for provided
     * list of printers, ordered by printed square meters descending.
     *
     * @param printerIds List of printer id's.
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeDto(s.date, sum(s.maxSpeedPrinted), sum(s.highSpeedPrinted), sum(s.productionPrinted), sum(s.highQualityPrinted), sum(s.specialtyPrinted), sum(s.backlitPrinted), sum(s.reliancePrinted), sum(s.otherPrinted)) " +
            "FROM SquareMeterPerPrintModeEntity s " +
            "WHERE s.printerId IN :printerIds " +
            "GROUP BY s.date " +
            "ORDER BY s.date ASC")
    List<SquareMeterPerPrintModeDto> getPrinters(@Param("printerIds") List<String> printerIds);

    /**
     * Retrieve aggregated printed square meters for provided period of interest
     * and list of printers, ordered by printed square meters descending.
     *
     * @param from       Period of interest starting date inclusive.
     * @param to         Period of interest ending date inclusive.
     * @param printerIds List of printer id's.
     * @return A <b>list of SquareMeterPerPrintModeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeDto(s.date, sum(s.maxSpeedPrinted), sum(s.highSpeedPrinted), sum(s.productionPrinted), sum(s.highQualityPrinted), sum(s.specialtyPrinted), sum(s.backlitPrinted), sum(s.reliancePrinted), sum(s.otherPrinted)) " +
            "FROM SquareMeterPerPrintModeEntity s " +
            "WHERE (s.date BETWEEN :from AND :to) " +
            "AND (s.printerId IN :printerIds) " +
            "GROUP BY s.date " +
            "ORDER BY s.date ASC")
    List<SquareMeterPerPrintModeDto> getPrintersForPeriod(@Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    /**
     * Retrieve min and max date from the table.
     *
     * @return A <b>PeriodDto object</b> containing the min and max date. <b>From</b>
     * contains the <b>min</b> and <b>to</b> contains the <b>max</b> date.
     */
    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(s.date), max(s.date)) " +
            "FROM SquareMeterPerPrintModeEntity s")
    PeriodDto getAvailableTimePeriod();

    /**
     * Retrieve all available printers from the table.
     *
     * @return A <b>list of Strings</b>, each one representing a <b>printer id</b>.
     */
    @Query("SELECT s.printerId " +
            "FROM SquareMeterPerPrintModeEntity s " +
            "GROUP BY s.printerId " +
            "ORDER BY s.printerId ASC")
    List<String> getAvailablePrinters();
}
