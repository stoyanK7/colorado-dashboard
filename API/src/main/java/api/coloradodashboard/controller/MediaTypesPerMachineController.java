package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.dto.MediaTypesPerMachineDto;
import api.coloradodashboard.entity.MediaTypesPerMachineEntity;
import api.coloradodashboard.repository.MediaTypesPerMachineRepository;
import api.coloradodashboard.factory.BaseServiceFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Media types per machine' chart.
 * Returns a list of MediaTypesPerMachineDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("MediaTypesPerMachine")
@RestController
public class MediaTypesPerMachineController
        extends BaseController<MediaTypesPerMachineEntity, MediaTypesPerMachineDto> {
    public MediaTypesPerMachineController(BaseServiceFactory<MediaTypesPerMachineEntity, MediaTypesPerMachineDto> baseServiceFactory,
                                          MediaTypesPerMachineRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
