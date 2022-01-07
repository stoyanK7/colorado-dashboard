package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.service.base.BaseServiceFactory;
import api.coloradodashboard.dto.MediaCategoryUsageDto;
import api.coloradodashboard.repository.MediaCategoryUsageRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Media category usage' chart.
 * Returns a list of MediaCategoryUsageDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("MediaCategoryUsage")
@RestController
public class MediaCategoryUsageController extends BaseController<MediaCategoryUsageDto> {
    public MediaCategoryUsageController(BaseServiceFactory<MediaCategoryUsageDto> baseServiceFactory,
                                        MediaCategoryUsageRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
