package api.coloradodashboard.repository;

import api.coloradodashboard.dto.MediaTypesPerMachineDto;
import api.coloradodashboard.entity.MediaTypesPerMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository providing access to the table with all data for <b>Media types per
 * machine</b>.
 */
public interface MediaTypesPerMachineRepository extends JpaRepository<MediaTypesPerMachineEntity, Long>,
        BaseRepository<MediaTypesPerMachineDto> {

}
