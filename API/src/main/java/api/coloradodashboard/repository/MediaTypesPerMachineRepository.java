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
    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.mediaType, SUM(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE (:dateFormat is not null) " +
            "GROUP BY m.mediaType " +
            "ORDER BY sum(m.printedSquareMeters) DESC")
    List<MediaTypesPerMachineDto> getAllAggregated(String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.printerId, m.mediaType, SUM(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE (:dateFormat is not null) " +
            "GROUP BY m.printerId, m.mediaType " +
            "ORDER BY m.mediaType, m.printerId ASC")
    List<MediaTypesPerMachineDto> getAllNonAggregated(String dateFormat);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "AND (:dateFormat is not null)" +
            "GROUP BY m.mediaType " +
            "ORDER BY sum(m.printedSquareMeters) DESC")
    List<MediaTypesPerMachineDto> getAllForPeriodAggregated(String dateFormat,
                                                            Date from,
                                                            Date to);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.printerId, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "AND (:dateFormat is not null)" +
            "GROUP BY m.printerId, m.mediaType " +
            "ORDER BY m.mediaType, m.printerId ASC")
    List<MediaTypesPerMachineDto> getAllForPeriodNonAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "AND (:dateFormat is not null)" +
            "GROUP BY m.mediaType " +
            "ORDER BY SUM(m.printedSquareMeters) ASC")
    List<MediaTypesPerMachineDto> getAllForPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.printerId, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "AND (:dateFormat is not null)" +
            "GROUP BY m.printerId, m.mediaType " +
            "ORDER BY m.mediaType, m.printerId ASC")
    List<MediaTypesPerMachineDto> getAllForPrintersNonAggregated(@Param("dateFormat") String dateFormat, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "AND (:dateFormat is not null)" +
            "GROUP BY m.mediaType " +
            "ORDER BY SUM(m.printedSquareMeters) DESC")
    List<MediaTypesPerMachineDto> getAllForPeriodAndPrintersAggregated(@Param("dateFormat") String dateFormat, @Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    @Query("SELECT new api.coloradodashboard.dto.MediaTypesPerMachineDto(m.printerId, m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "AND (:dateFormat is not null)" +
            "GROUP BY m.printerId, m.mediaType " +
            "ORDER BY m.mediaType, m.printerId ASC")
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
