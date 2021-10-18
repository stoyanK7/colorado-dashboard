package com.example.canondashboardapi.service.interfaces;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.util.Date;
import java.util.List;

public interface IFiltrationService {
    List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(Date startingDate, Date endingDate);
}
