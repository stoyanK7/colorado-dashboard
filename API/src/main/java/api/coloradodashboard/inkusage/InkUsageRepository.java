package api.coloradodashboard.inkusage;

import api.coloradodashboard.PeriodDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Ink usage</b>.
 */
public interface InkUsageRepository extends JpaRepository<InkUsageEntity, Long> {
    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(i.date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    <T> List<T> getAllAggregated();

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(i.date, i.printerId, i.cyanLitresUsed, i.magentaLitresUsed, i.yellowLitresUsed, i.blackLitresUsed) " +
            "FROM InkUsageEntity i " +
            "ORDER BY i.date ASC")
    <T> List<T> getAllNonAggregated();

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(i.date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    <T> List<T> getAllForPeriodAggregated(@Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(i.date, i.printerId, i.cyanLitresUsed, i.magentaLitresUsed, i.yellowLitresUsed, i.blackLitresUsed) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "ORDER BY i.date ASC")
    <T> List<T> getAllForPeriodNonAggregated(@Param("from") Date from, @Param("to") Date to);




    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(i.date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getPrinters(@Param("printerIds") List<String> printerIds);

    /**
     * Retrieve aggregated ink usage for cyan, magenta, yellow and black for provided period of interest
     * and list of printers, ordered by date ascending.
     *
     * @param from       Period of interest starting date inclusive.
     * @param to         Period of interest ending date inclusive.
     * @param printerIds List of printer id's.
     * @return A <b>list of InkUsageDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(i.date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getPrintersForPeriod(@Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    /**
     * Retrieve min and max date from the table.
     *
     * @return A <b>PeriodDto object</b> containing the min and max date.<b>From</b>
     * contains the <b>min</b> and <b>to</b> contains the <b>max</b> date.
     */
    @Query("SELECT new api.coloradodashboard.PeriodDto(min(i.date), max(i.date)) " +
            "FROM InkUsageEntity i")
    PeriodDto getAvailableTimePeriod();

    /**
     * Retrieve all available printers from the table.
     *
     * @return A <b>list of Strings</b>, each one representing a <b>printer id</b>.
     */
    @Query("SELECT i.printerId " +
            "FROM InkUsageEntity i " +
            "GROUP BY i.printerId " +
            "ORDER BY i.printerId ASC")
    List<String> getAvailablePrinters();
}
