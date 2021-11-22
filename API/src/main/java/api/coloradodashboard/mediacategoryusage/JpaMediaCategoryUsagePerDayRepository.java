package api.coloradodashboard.mediacategoryusage;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO: Add javadoc
 */
public interface JpaMediaCategoryUsagePerDayRepository extends JpaRepository<MediaCategoryUsagePerDay, Long> {
    MediaCategoryUsagePerDay findTotalPrintSquareMeterPerMediaCategoryPerDaysById(long id);
}
