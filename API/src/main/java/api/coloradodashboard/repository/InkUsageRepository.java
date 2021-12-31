package api.coloradodashboard.repository;

import api.coloradodashboard.dto.InkUsageDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.InkUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Ink usage</b>.
 */
public interface InkUsageRepository extends JpaRepository<InkUsageEntity, Long>,
        BaseRepository<InkUsageDto> {
    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "GROUP BY formatted_date " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllNonAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllForPeriodAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "GROUP BY formatted_date " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY formatted_date " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<InkUsageDto> getAllForPeriodAndPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(i.date), max(i.date)) " +
            "FROM InkUsageEntity i")
    PeriodDto getAvailableTimePeriod();

    @Query("SELECT i.printerId " +
            "FROM InkUsageEntity i " +
            "GROUP BY i.printerId " +
            "ORDER BY i.printerId ASC")
    List<String> getAvailablePrinters();

    @Query("SELECT m.mediaCategory " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY m.mediaCategory")
    List<String> getChartDataKeys();
}
