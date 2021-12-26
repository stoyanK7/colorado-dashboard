package api.coloradodashboard.inkusage;

import api.coloradodashboard.BaseController;
import api.coloradodashboard.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Ink usage</i></b> chart.
 * Returns a list of <b>InkUsageDto</b> objects or <b>404</b> if no data is present.
 */
@RequestMapping("InkUsage")
@RestController
public class InkUsageController extends BaseController<InkUsageDto> {
    public InkUsageController(BaseService<InkUsageDto> service) {
        super(service);
    }
}
