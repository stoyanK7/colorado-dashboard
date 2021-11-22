package api.coloradodashboard.mediacategoryusage;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO: Add javadoc
 */
public interface JpaTotalPrintSquareMeterPerMediaCategoryPerDay extends JpaRepository<TotalPrintSquareMeterPerMediaCategoryPerDay, Long> {
    TotalPrintSquareMeterPerMediaCategoryPerDay findTotalPrintSquareMeterPerMediaCategoryPerDaysById(long id);
}
