package api.coloradodashboard.service.base;

import api.coloradodashboard.entity.base.BaseEntity;
import api.coloradodashboard.repository.base.BaseRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class BaseServiceFactory<E extends BaseEntity, D> {
    public BaseService<E, D> getBaseService(BaseRepository<E, D> repository) {
        return new BaseService<>(repository);
    }
}
