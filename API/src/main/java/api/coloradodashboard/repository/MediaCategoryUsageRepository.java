package api.coloradodashboard.repository;

import api.coloradodashboard.dto.MediaCategoryUsageDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.InkUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Media categories
 * usage</b>.
 */
public interface MediaCategoryUsageRepository extends JpaRepository<InkUsageEntity, Long>,
        BaseRepository<MediaCategoryUsageDto> {
    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, SUM(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaCategory, SUM(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY formatted_date, m.printerId " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllNonAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "GROUP BY formatted_date " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE i.printerId IN :printerIds " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY formatted_date " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM InkUsageEntity i " +
            "WHERE (i.date BETWEEN :from AND :to) " +
            "AND (i.printerId IN :printerIds) " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodAndPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.PeriodDto(min(m.date), max(m.date)) " +
            "FROM MediaCategoryUsageEntity m")
    PeriodDto getAvailableTimePeriod();

    @Query("SELECT m.printerId " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY m.printerId " +
            "ORDER BY m.printerId ASC")
    List<String> getAvailablePrinters();

    @Query("SELECT m.mediaCategory " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY m.mediaCategory")
    List<String> getChartDataKeys();
}
