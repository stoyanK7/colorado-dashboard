package api.coloradodashboard.factory;

import api.coloradodashboard.entity.base.BaseEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import api.coloradodashboard.service.base.BaseService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class BaseServiceFactory<E extends BaseEntity, D> {
    public BaseService<E, D> getBaseService(BaseRepository<E, D> repository) {
        return new BaseService<>(repository);
    }
}
