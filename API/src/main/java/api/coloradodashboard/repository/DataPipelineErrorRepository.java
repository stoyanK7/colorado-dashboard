package api.coloradodashboard.repository;

import api.coloradodashboard.dto.DataPipelineErrorDto;
import api.coloradodashboard.entity.DataPipelineErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository providing access to the table with all data for data pipeline errors.
 */
public interface DataPipelineErrorRepository
        extends JpaRepository<DataPipelineErrorEntity, Long> {
    @Query("SELECT new api.coloradodashboard.dto.DataPipelineErrorDto(d.id, d.passed, d.step, d.affectedGraphs, d.location, d.dateTime, d.log) " +
            "FROM DataPipelineErrorEntity d " +
            "WHERE d.dateTime = ( " +
            "SELECT max(d.dateTime) " +
            "from DataPipelineErrorEntity d)")
    DataPipelineErrorDto getLatest();

    @Query("SELECT new api.coloradodashboard.dto.DataPipelineErrorDto(d.id, d.passed, d.step, d.dateTime) " +
            "FROM DataPipelineErrorEntity d " +
            "ORDER BY d.dateTime DESC")
    List<DataPipelineErrorDto> getAll();

    @Query("SELECT new api.coloradodashboard.dto.DataPipelineErrorDto(d.id, d.passed, d.step, d.affectedGraphs, d.location, d.dateTime, d.log) " +
            "FROM DataPipelineErrorEntity d " +
            "WHERE d.id = :id")
    DataPipelineErrorDto getOneById(Long id);
}
