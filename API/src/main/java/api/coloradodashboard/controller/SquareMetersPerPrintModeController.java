package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.service.base.BaseServiceFactory;
import api.coloradodashboard.dto.SquareMetersPerPrintModeDto;
import api.coloradodashboard.repository.SquareMetersPerPrintModeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Square meters per print mode' chart.
 * Returns a list of SquareMetersPerPrintModeDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("SquareMetersPerPrintMode")
@RestController
public class SquareMetersPerPrintModeController extends BaseController<SquareMetersPerPrintModeDto> {
    public SquareMetersPerPrintModeController(BaseServiceFactory<SquareMetersPerPrintModeDto> baseServiceFactory,
                                              SquareMetersPerPrintModeRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
