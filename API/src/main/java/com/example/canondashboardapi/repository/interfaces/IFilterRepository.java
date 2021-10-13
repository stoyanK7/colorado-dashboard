package com.example.canondashboardapi.repository.interfaces;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.util.Date;
import java.util.List;

public interface IFilterRepository {

    List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(Date startingDate, Date endingDate);
}
