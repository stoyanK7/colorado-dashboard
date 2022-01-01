package api.coloradodashboard.repository;

import api.coloradodashboard.dto.SquareMetersPerPrintModeDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.SquareMetersPerPrintModeEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SquareMetersPerPrintModeRepository extends JpaRepository<SquareMetersPerPrintModeEntity, Long>,
        BaseRepository<SquareMetersPerPrintModeDto> {
    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, SUM(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, SUM(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllNonAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.printerId IN :printerIds " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE s.printerId IN :printerIds " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE (s.date BETWEEN :from AND :to) " +
            "AND (s.printerId IN :printerIds) " +
            "GROUP BY formatted_date, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.SquareMetersPerPrintModeDto(DATE_FORMAT(s.date, :dateFormat) AS formatted_date, s.printerId, s.printMode, sum(s.printedSquareMeters)) " +
            "FROM SquareMetersPerPrintModeEntity s " +
            "WHERE (s.date BETWEEN :from AND :to) " +
            "AND (s.printerId IN :printerIds) " +
            "GROUP BY formatted_date, s.printerId, s.printMode " +
            "ORDER BY formatted_date ASC")
    List<SquareMetersPerPrintModeDto> getAllForPeriodAndPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

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
