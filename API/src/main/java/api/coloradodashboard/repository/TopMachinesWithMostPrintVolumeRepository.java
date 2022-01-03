package api.coloradodashboard.repository;

import api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.TopMachinesWithMostPrintVolumeEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TopMachinesWithMostPrintVolumeRepository extends JpaRepository<TopMachinesWithMostPrintVolumeEntity, Long>,
        BaseRepository<TopMachinesWithMostPrintVolumeDto> {
    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(t.printerId, SUM(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE (:dateFormat is not null) " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllAggregated(String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(DATE_FORMAT(t.date, :dateFormat) AS formatted_date, t.printerId, SUM(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "GROUP BY formatted_date, t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllNonAggregated(String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.date BETWEEN :from AND :to " +
            "AND (:dateFormat is not null) " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPeriodAggregated(String dateFormat,
                                                                      Date from,
                                                                      Date to);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(DATE_FORMAT(t.date, :dateFormat) AS formatted_date, t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, t.printerId " +
            "ORDER BY formatted_date ASC, sum(t.printedSquareMeters) DESC ")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPeriodNonAggregated(String dateFormat,
                                                                         Date from,
                                                                         Date to);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.printerId IN :printerIds " +
            "AND (:dateFormat is not null) " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPrintersAggregated(String dateFormat,
                                                                        List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(DATE_FORMAT(t.date, :dateFormat) AS formatted_date, t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.printerId IN :printerIds " +
            "GROUP BY formatted_date, t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPrintersNonAggregated(String dateFormat,
                                                                           List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE (t.date BETWEEN :from AND :to) " +
            "AND (t.printerId IN :printerIds) " +
            "AND (:dateFormat is not null) " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPeriodAndPrintersAggregated(String dateFormat,
                                                                                 Date from,
                                                                                 Date to,
                                                                                 List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto(DATE_FORMAT(t.date, :dateFormat) AS formatted_date, t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE (t.date BETWEEN :from AND :to) " +
            "AND (t.printerId IN :printerIds) " +
            "GROUP BY formatted_date, t.printerId " +
            "ORDER BY formatted_date ASC, sum(t.printedSquareMeters) DESC ")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPeriodAndPrintersNonAggregated(String dateFormat,
                                                                                    Date from,
                                                                                    Date to,
                                                                                    List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(t.date), max(t.date)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t")
    PeriodDto getAvailableTimePeriod();

    @Query("SELECT t.printerId " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "GROUP BY t.printerId " +
            "ORDER BY t.printerId ASC")
    List<String> getAvailablePrinters();

    @Query("SELECT t.printerId " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "GROUP BY t.printerId")
    List<String> getChartDataKeys();
}
