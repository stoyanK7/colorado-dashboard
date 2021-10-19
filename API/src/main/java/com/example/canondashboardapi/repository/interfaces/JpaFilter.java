package com.example.canondashboardapi.repository.interfaces;

import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface JpaFilter extends JpaRepository<TotalPrintSquareMeterPerMediaCategoryPerDay, Long> {
    List<TotalPrintSquareMeterPerMediaCategoryPerDay> findAllByDateTimeBetween(Date startingDate, Date endingDate);

}
