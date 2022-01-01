package api.coloradodashboard.service.base;

import api.coloradodashboard.repository.base.BaseRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class BaseServiceFactory<T> {
    public BaseService<T> getBaseService(BaseRepository<T> repository) {
        return new BaseService<T>(repository);
    }
}
