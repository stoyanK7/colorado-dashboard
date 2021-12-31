package api.coloradodashboard.controller;

import api.coloradodashboard.service.BaseServiceFactory;
import api.coloradodashboard.dto.TopMachinesWithMostPrintVolumeDto;
import api.coloradodashboard.repository.TopMachinesWithMostPrintVolumeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Top machines with most print volume</i></b> chart.
 * Returns a list of <b>TopMachinesWithMostPrintVolumeDto</b> objects or <b>404</b> if no data is present.
 */
@RequestMapping("TopMachinesWithMostPrintVolume")
@RestController
public class TopMachinesWithMostPrintVolumeController extends BaseController<TopMachinesWithMostPrintVolumeDto> {
    public TopMachinesWithMostPrintVolumeController(BaseServiceFactory<TopMachinesWithMostPrintVolumeDto> baseServiceFactory,
                                                    TopMachinesWithMostPrintVolumeRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
