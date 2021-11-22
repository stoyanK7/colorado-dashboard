package api.coloradodashboard.inkusage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInkUsagePerDayRepository extends JpaRepository<InkUsagePerDay, Long> {
}
