package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseChartController;
import api.coloradodashboard.entity.InkUsageEntity;
import api.coloradodashboard.dto.InkUsageDto;
import api.coloradodashboard.factory.BaseServiceFactory;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Ink usage' chart.
 * Returns a list of InkUsageDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("InkUsage")
@RestController
public class InkUsageController
        extends BaseChartController<InkUsageEntity, InkUsageDto> {
    public InkUsageController(BaseServiceFactory<InkUsageEntity, InkUsageDto> baseServiceFactory,
                              BaseRepository<InkUsageEntity, InkUsageDto> repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
