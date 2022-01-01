package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.service.base.BaseServiceFactory;
import api.coloradodashboard.dto.InkUsageDto;
import api.coloradodashboard.repository.InkUsageRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Ink usage</i></b> chart.
 * Returns a list of <b>InkUsageDto</b> objects or <b>404</b> if no data is present.
 */
@RequestMapping("InkUsage")
@RestController
public class InkUsageController extends BaseController<InkUsageDto> {
    public InkUsageController(BaseServiceFactory<InkUsageDto> baseServiceFactory,
                              InkUsageRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
