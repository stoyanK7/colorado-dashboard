package com.example.canondashboardapi.repository.interfaces;

import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO: Add javadoc
 */
public interface JpaRepo extends JpaRepository<TotalPrintSquareMeterPerMediaCategoryPerDay,Long> {
    TotalPrintSquareMeterPerMediaCategoryPerDay findTotalPrintSquareMeterPerMediaCategoryPerDaysById(long id);
}
