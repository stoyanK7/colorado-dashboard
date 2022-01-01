package api.coloradodashboard.repository;

import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto;
import api.coloradodashboard.entity.TopMachinesWithMostPrintVolumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Top machines with
 * most print volume</b>.
 */
public interface TopMachinesWithMostPrintVolumeRepository extends JpaRepository<TopMachinesWithMostPrintVolumeEntity, Long>,
        BaseRepository<TopMachinesWithMostPrintVolumeDto> {
    /**
     * Retrieve aggregated printed square meters for all printers for all time
     * grouping by printer, ordered by printed square meters descending.
     *
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAll();

    /**
     * Retrieve aggregated printed square meters for all printers for provided
     * period of interest, ordered by printed square meters descending.
     *
     * @param from Period of interest starting date inclusive.
     * @param to   Period of interest ending date inclusive.
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.date BETWEEN :from AND :to " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPeriod(@Param("from") Date from, @Param("to") Date to);

    /**
     * Retrieve aggregated printed square meters for all time for provided
     * list of printers, ordered by printed square meters descending.
     *
     * @param printerIds List of printer id's.
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.printerId IN :printerIds " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getPrinters(@Param("printerIds") List<String> printerIds);

    /**
     * Retrieve aggregated printed square meters for provided period of interest
     * and list of printers, ordered by printed square meters descending.
     *
     * @param from       Period of interest starting date inclusive.
     * @param to         Period of interest ending date inclusive.
     * @param printerIds List of printer id's.
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE (t.date BETWEEN :from AND :to) " +
            "AND (t.printerId IN :printerIds) " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getPrintersForPeriod(@Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    /**
     * Retrieve min and max date from the table.
     *
     * @return A <b>PeriodDto object</b> containing the min and max date. <b>From</b>
     * contains the <b>min</b> and <b>to</b> contains the <b>max</b> date.
     */
    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(t.date), max(t.date)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t")
    PeriodDto getAvailableTimePeriod();

    /**
     * Retrieve all available printers from the table.
     *
     * @return A <b>list of Strings</b>, each one representing a <b>printer id</b>.
     */
    @Query("SELECT t.printerId " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "GROUP BY t.printerId " +
            "ORDER BY t.printerId ASC")
    List<String> getAvailablePrinters();
}
