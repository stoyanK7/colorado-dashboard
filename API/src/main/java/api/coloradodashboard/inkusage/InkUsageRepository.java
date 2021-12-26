package api.coloradodashboard.inkusage;

import api.coloradodashboard.BaseRepository;
import api.coloradodashboard.PeriodDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Ink usage</b>.
 */
public interface InkUsageRepository extends JpaRepository<InkUsageEntity, Long>, BaseRepository<InkUsageDto> {
    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllAggregated();

//    @Query("SELECT new api.coloradodashboard.inkusage.TestDto(DATE_FORMAT(i.date,'%Y/%u'), sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
//            "FROM InkUsageEntity i " +
//            "GROUP BY DATE_FORMAT(i.date,'%Y/%u')")
//    List<TestDto> test();

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), i.printerId, i.cyanLitresUsed, i.magentaLitresUsed, i.yellowLitresUsed, i.blackLitresUsed) " +
            "FROM InkUsageEntity i " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllNonAggregated();

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllForPeriodAggregated(@Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), i.printerId, i.cyanLitresUsed, i.magentaLitresUsed, i.yellowLitresUsed, i.blackLitresUsed) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllForPeriodNonAggregated(@Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllForPrintersAggregated(@Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), i.printerId, i.cyanLitresUsed, i.magentaLitresUsed, i.yellowLitresUsed, i.blackLitresUsed) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllForPrintersNonAggregated(@Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllForPeriodAndPrintersAggregated(@Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, '%Y %b %d'), i.printerId, i.cyanLitresUsed, i.magentaLitresUsed, i.yellowLitresUsed, i.blackLitresUsed) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY i.date " +
            "ORDER BY i.date ASC")
    List<InkUsageDto> getAllForPeriodAndPrintersNonAggregated(@Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.PeriodDto(min(i.date), max(i.date)) " +
            "FROM InkUsageEntity i")
    PeriodDto getAvailableTimePeriod();

    @Query("SELECT i.printerId " +
            "FROM InkUsageEntity i " +
            "GROUP BY i.printerId " +
            "ORDER BY i.printerId ASC")
    List<String> getAvailablePrinters();
}
