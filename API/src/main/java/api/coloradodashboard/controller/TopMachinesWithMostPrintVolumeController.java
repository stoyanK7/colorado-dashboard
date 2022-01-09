package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.entity.TopMachinesWithMostPrintVolumeEntity;
import api.coloradodashboard.factory.BaseServiceFactory;
import api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Top machines with most print volume' chart.
 * Returns a list of TopMachinesWithMostPrintVolumeDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("TopMachinesWithMostPrintVolume")
@RestController
public class TopMachinesWithMostPrintVolumeController
        extends BaseController<TopMachinesWithMostPrintVolumeEntity, TopMachinesWithMostPrintVolumeDto> {
    public TopMachinesWithMostPrintVolumeController(BaseServiceFactory<TopMachinesWithMostPrintVolumeEntity, TopMachinesWithMostPrintVolumeDto> baseServiceFactory,
                                                    BaseRepository<TopMachinesWithMostPrintVolumeEntity, TopMachinesWithMostPrintVolumeDto> repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
