package api.coloradodashboard.repository;

import api.coloradodashboard.dto.MediaTypesPerMachineDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.MediaTypesPerMachineEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Media types per
 * machine</b>.
 */
public interface MediaTypesPerMachineRepository extends JpaRepository<MediaTypesPerMachineEntity, Long>,
        BaseRepository<MediaTypesPerMachineDto> {
    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaType, SUM(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "GROUP BY formatted_date, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaType, SUM(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "GROUP BY formatted_date, m.printerId, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllNonAggregated(@Param("dateFormat") String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllForPeriodAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY formatted_date, m.printerId, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "GROUP BY formatted_date, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "GROUP BY formatted_date, m.printerId, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "GROUP BY formatted_date, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(DATE_FORMAT(m.date, :dateFormat) AS formatted_date, m.printerId, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "GROUP BY formatted_date, m.printerId, m.mediaType " +
            "ORDER BY formatted_date ASC")
    List<MediaTypesPerMachineDto> getAllForPeriodAndPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.PeriodDto(min(m.date), max(m.date)) " +
            "FROM MediaTypesPerMachineEntity m")
    PeriodDto getAvailableTimePeriod();

    @Query("SELECT m.printerId " +
            "FROM MediaTypesPerMachineEntity m " +
            "GROUP BY m.printerId " +
            "ORDER BY m.printerId ASC")
    List<String> getAvailablePrinters();

    @Query("SELECT m.mediaType " +
            "FROM MediaTypesPerMachineEntity m " +
            "GROUP BY m.mediaType")
    List<String> getChartDataKeys();
}
