package api.coloradodashboard.controller;

import api.coloradodashboard.service.BaseServiceFactory;
import api.coloradodashboard.dto.SquareMetersPerPrintModeDto;
import api.coloradodashboard.repository.SquareMetersPerPrintModeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Square meters per print mode</i></b> chart.
 * Returns a list of <b>SquareMetersPerPrintModeDto</b> objects or <b>404</b> if no data is present.
 */
@RequestMapping("SquareMetersPerPrintMode")
@RestController
public class SquareMetersPerPrintModeController extends BaseController<SquareMetersPerPrintModeDto> {
    public SquareMetersPerPrintModeController(BaseServiceFactory<SquareMetersPerPrintModeDto> baseServiceFactory,
                                              SquareMetersPerPrintModeRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
