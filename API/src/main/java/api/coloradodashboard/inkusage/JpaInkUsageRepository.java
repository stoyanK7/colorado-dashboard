package api.coloradodashboard.inkusage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInkUsageRepository extends JpaRepository<InkUsage, Long> {
}
