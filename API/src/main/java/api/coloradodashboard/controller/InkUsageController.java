package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.service.base.BaseServiceFactory;
import api.coloradodashboard.dto.InkUsageDto;
import api.coloradodashboard.repository.InkUsageRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Ink usage' chart.
 * Returns a list of InkUsageDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("InkUsage")
@RestController
public class InkUsageController extends BaseController<InkUsageDto> {
    public InkUsageController(BaseServiceFactory<InkUsageDto> baseServiceFactory,
                              InkUsageRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
