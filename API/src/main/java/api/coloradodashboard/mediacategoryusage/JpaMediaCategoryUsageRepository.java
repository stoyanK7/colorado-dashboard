package api.coloradodashboard.mediacategoryusage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMediaCategoryUsageRepository extends JpaRepository<MediaCategoryUsage, Long> {
    List<MediaCategoryUsage> getAllByOrderByDateAsc();
}
