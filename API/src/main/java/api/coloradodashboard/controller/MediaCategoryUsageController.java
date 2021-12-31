package api.coloradodashboard.controller;

import api.coloradodashboard.service.BaseServiceFactory;
import api.coloradodashboard.dto.MediaCategoryUsageDto;
import api.coloradodashboard.repository.MediaCategoryUsageRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Media category usage</i></b> chart.
 * Returns a list of <b>MediaCategoryUsageDto</b> objects or <b>404</b> if no data is present.
 */
@RequestMapping("MediaCategoryUsage")
@RestController
public class MediaCategoryUsageController extends BaseController<MediaCategoryUsageDto> {
    public MediaCategoryUsageController(BaseServiceFactory<MediaCategoryUsageDto> baseServiceFactory,
                                        MediaCategoryUsageRepository repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
