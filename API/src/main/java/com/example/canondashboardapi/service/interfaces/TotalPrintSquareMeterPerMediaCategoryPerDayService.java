package com.example.canondashboardapi.service.interfaces;

import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.util.List;

// TODO: Make Generic
public interface TotalPrintSquareMeterPerMediaCategoryPerDayService {
    List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll();

    void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType);
}
