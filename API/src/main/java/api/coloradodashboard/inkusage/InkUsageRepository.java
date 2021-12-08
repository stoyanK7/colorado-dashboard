package api.coloradodashboard.inkusage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InkUsageRepository extends JpaRepository<InkUsage, Long> {
    List<InkUsage> getAllByOrderByDateAsc();
}
