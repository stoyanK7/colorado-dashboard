package com.example.canondashboardapi.service.interfaces;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.util.List;

public interface ITotalPrintSquareMeterPerMediaCategoryPerDayService {
    List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll();

    void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType);
}
