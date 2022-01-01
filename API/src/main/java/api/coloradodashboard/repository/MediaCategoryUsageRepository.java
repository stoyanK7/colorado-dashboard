package api.coloradodashboard.repository;

import api.coloradodashboard.dto.MediaCategoryUsageDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.MediaCategoryUsageEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Media categories
 * usage</b>.
 */
public interface MediaCategoryUsageRepository extends JpaRepository<MediaCategoryUsageEntity, Long>,
        BaseRepository<MediaCategoryUsageDto> {
    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, SUM(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaCategory, SUM(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY formatted_date, m.printerId, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllNonAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, m.printerId, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "GROUP BY formatted_date, m.printerId, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaCategory, sum(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "GROUP BY formatted_date, m.printerId, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<MediaCategoryUsageDto> getAllForPeriodAndPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(m.date), max(m.date)) " +
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
