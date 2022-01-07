package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.service.base.BaseServiceFactory;
import api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto;
import api.coloradodashboard.repository.TopMachinesWithMostPrintVolumeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Top machines with most print volume' chart.
 * Returns a list of TopMachinesWithMostPrintVolumeDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("TopMachinesWithMostPrintVolume")
@RestController
public class TopMachinesWithMostPrintVolumeController extends BaseController<TopMachinesWithMostPrintVolumeDto> {
    public TopMachinesWithMostPrintVolumeController(BaseServiceFactory<TopMachinesWithMostPrintVolumeDto> baseServiceFactory,
                                                    TopMachinesWithMostPrintVolumeRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
