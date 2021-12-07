package api.coloradodashboard.mediatypespermachine;

import api.coloradodashboard.PeriodDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Media types per
 * machine</b>.
 */
public interface MediaTypesPerMachineRepository extends JpaRepository<MediaTypesPerMachineEntity, Long> {
    @Query("SELECT new api.coloradodashboard.mediatypespermachine.MediaTypesPerMachineDto(m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "GROUP BY m.mediaType " +
            "ORDER BY sum(m.printedSquareMeters) DESC")
    List<MediaTypesPerMachineDto> getAll();

    @Query("SELECT new api.coloradodashboard.mediatypespermachine.MediaTypesPerMachineDto(m.mediaType, sum(m.printedSquareMeters)) " +
            "FROM MediaTypesPerMachineEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY m.mediaType " +
            "ORDER BY sum(m.printedSquareMeters) DESC")
    List<MediaTypesPerMachineDto> getAllForPeriod(@Param("from") Date from, @Param("to") Date to);


    /**
     * Retrieve min and max date from the table.
     *
     * @return A <b>PeriodDto object</b> containing the min and max date. <b>From</b>
     * contains the <b>min</b> and <b>to</b> contains the <b>max</b> date.
     */
    @Query("SELECT new api.coloradodashboard.PeriodDto(min(m.date), max(m.date)) " +
            "FROM MediaTypesPerMachineEntity m")
    PeriodDto getAvailableTimePeriod();

    /**
     * Retrieve all available printers from the table.
     *
     * @return A <b>list of Strings</b>, each one representing a <b>printer id</b>.
     */
    @Query("SELECT m.printerId " +
            "FROM MediaTypesPerMachineEntity m " +
            "GROUP BY m.printerId " +
            "ORDER BY m.printerId ASC")
    List<String> getAvailablePrinters();
}
