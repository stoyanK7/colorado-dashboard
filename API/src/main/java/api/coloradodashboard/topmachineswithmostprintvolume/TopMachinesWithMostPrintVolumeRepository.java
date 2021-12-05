package api.coloradodashboard.topmachineswithmostprintvolume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TopMachinesWithMostPrintVolumeRepository extends JpaRepository<TopMachinesWithMostPrintVolumeEntity, Long> {
    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAll();

    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.date BETWEEN :from AND :to " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getAllForPeriod(@Param("from") Date from, @Param("to") Date to);

    @Query("SELECT new api.coloradodashboard.topmachineswithmostprintvolume.TopMachinesWithMostPrintVolumeDto(t.printerId, sum(t.printedSquareMeters)) " +
            "FROM TopMachinesWithMostPrintVolumeEntity t " +
            "WHERE t.printerId IN :printerIds " +
            "GROUP BY t.printerId " +
            "ORDER BY sum(t.printedSquareMeters) DESC")
    List<TopMachinesWithMostPrintVolumeDto> getPrinters(@Param("printerIds") List<String> printerIds);
}
