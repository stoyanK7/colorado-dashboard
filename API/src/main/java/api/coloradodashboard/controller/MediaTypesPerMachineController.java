package api.coloradodashboard.controller;

import api.coloradodashboard.dto.MediaTypesPerMachineDto;
import api.coloradodashboard.repository.MediaTypesPerMachineRepository;
import api.coloradodashboard.service.BaseServiceFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Media types per machine</i></b> chart.
 * Returns a list of <b>MediaTypesPerMachineDto</b> objects or <b>404</b> if no data is present.
 */
@RequestMapping("MediaTypesPerMachine")
@RestController
public class MediaTypesPerMachineController extends BaseController<MediaTypesPerMachineDto> {
    public MediaTypesPerMachineController(BaseServiceFactory<MediaTypesPerMachineDto> baseServiceFactory,
                                          MediaTypesPerMachineRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
