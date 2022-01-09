package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.entity.SquareMetersPerPrintModeEntity;
import api.coloradodashboard.factory.BaseServiceFactory;
import api.coloradodashboard.dto.SquareMetersPerPrintModeDto;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Square meters per print mode' chart.
 * Returns a list of SquareMetersPerPrintModeDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("SquareMetersPerPrintMode")
@RestController
public class SquareMetersPerPrintModeController
        extends BaseController<SquareMetersPerPrintModeEntity, SquareMetersPerPrintModeDto> {
    public SquareMetersPerPrintModeController(BaseServiceFactory<SquareMetersPerPrintModeEntity, SquareMetersPerPrintModeDto> baseServiceFactory,
                                              BaseRepository<SquareMetersPerPrintModeEntity, SquareMetersPerPrintModeDto> repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
