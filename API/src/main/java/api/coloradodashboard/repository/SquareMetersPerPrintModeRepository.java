package api.coloradodashboard.repository;

import api.coloradodashboard.dto.SquareMetersPerPrintModeDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.SquareMetersPerPrintModeEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for 'Square meters per
 * print mode' chart.
 */
public interface SquareMetersPerPrintModeRepository
        extends BaseRepository<SquareMetersPerPrintModeEntity, SquareMetersPerPrintModeDto> {
    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, SUM(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllAggregated(String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, SUM(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllNonAggregated(String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodAggregated(String dateFormat,
                                                                Date from,
                                                                Date to);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodNonAggregated(String dateFormat,
                                                                   Date from,
                                                                   Date to);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.printerId IN :printerIds " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPrintersAggregated(String dateFormat,
                                                                  List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.printerId IN :printerIds " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPrintersNonAggregated(String dateFormat,
                                                                     List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE (s.date BETWEEN :from AND :to) " +
            "AND (s.printerId IN :printerIds) " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodAndPrintersAggregated(String dateFormat,
                                                                           Date from,
                                                                           Date to,
                                                                           List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE (s.date BETWEEN :from AND :to) " +
            "AND (s.printerId IN :printerIds) " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodAndPrintersNonAggregated(String dateFormat,
                                                                              Date from,
                                                                              Date to,
                                                                              List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(s.date), max(s.date)) " +
            "FROM SquareMetersPerPrintModeEntity s")
    PeriodDto getAvailableTimePeriod();

    @Query("SELECT s.printerId " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "GROUP BY s.printerId " +
            "ORDER BY s.printerId ASC")
    List<String> getAvailablePrinters();

    @Query("SELECT s.printMode " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "GROUP BY s.printMode")
    List<String> getChartDataKeys();

}
