package api.coloradodashboard.mediacategoryusage;

import api.coloradodashboard.BaseRepository;
import api.coloradodashboard.PeriodDto;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Media categories
 * usage</b>.
 */
@Primary
public interface MediaCategoryUsageRepository extends JpaRepository<MediaCategoryUsageEntity, Long>, BaseRepository<Object[]> {
    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaCategory, SUM(m.printedSquareMeters)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY formatted_date, m.mediaCategory " +
            "ORDER BY formatted_date ASC")
    List<Object[]> getAllAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.inkusage.InkUsageDto(DATE_FORMAT(i.date, :dateFormat) AS formatted_date, i.printerId, sum(i.cyanLitresUsed), sum(i.magentaLitresUsed), sum(i.yellowLitresUsed), sum(i.blackLitresUsed)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY formatted_date, i.printerId " +
            "ORDER BY formatted_date ASC")
    List<Object[]> getAllNonAggregated(@Param("dateFormat") String dateFormat);

    @Query(value = "SET SESSION group_concat_max_len = 1000000;\n" +
            "SELECT\n" +
            "  GROUP_CONCAT(DISTINCT\n" +
            "    CONCAT(\n" +
            "      'ifnull(SUM(case when media_category = ''',\n" +
            "      media_category,\n" +
            "      ''' then printed_square_meters end),0) AS `',\n" +
            "      media_category, '`'\n" +
            "    )\n" +
            "  ) INTO @sql\n" +
            "FROM\n" +
            "  media_category_usage;\n" +
            "SET @sql = CONCAT('SELECT date, printer_id, ', @sql, '\n" +
            "                  FROM media_category_usage\n" +
            "                   GROUP BY date, printer_id');\n" +
            "\n" +
            "PREPARE stmt FROM @sql;\n" +
            "EXECUTE stmt;\n" +
            "DEALLOCATE PREPARE stmt;\n", nativeQuery = true)
    List<Object[]> getAllForPeriodAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    List<Object[]> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);


    List<Object[]> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);


    List<Object[]> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);


    List<Object[]> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    List<Object[]> getAllForPeriodAndPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

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
