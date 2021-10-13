package com.example.canondashboardapi.repository.interfaces;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepo extends JpaRepository<TotalPrintSquareMeterPerMediaCategoryPerDay,Long> {
    TotalPrintSquareMeterPerMediaCategoryPerDay findTotalPrintSquareMeterPerMediaCategoryPerDayByIdById(long id);
}
