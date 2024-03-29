package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseChartController;
import api.coloradodashboard.entity.MediaCategoryUsageEntity;
import api.coloradodashboard.factory.BaseServiceFactory;
import api.coloradodashboard.dto.MediaCategoryUsageDto;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for 'Media category usage' chart.
 * Returns a list of MediaCategoryUsageDto objects or 404/NOT FOUND if no data is present.
 */
@RequestMapping("MediaCategoryUsage")
@RestController
public class MediaCategoryUsageController
        extends BaseChartController<MediaCategoryUsageEntity, MediaCategoryUsageDto> {
    public MediaCategoryUsageController(BaseServiceFactory<MediaCategoryUsageEntity, MediaCategoryUsageDto> baseServiceFactory,
                                        BaseRepository<MediaCategoryUsageEntity, MediaCategoryUsageDto> repository) {
        super(baseServiceFactory.getBaseService(repository));
    }
}
