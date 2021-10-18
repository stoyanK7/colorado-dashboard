package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.IFilterRepository;
import com.example.canondashboardapi.repository.interfaces.JpaFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class JpaFiltrationRepository implements IFilterRepository {

    @Autowired
    JpaFilter jpa;

    /**
     * Retrieves data for the timeframe
     * @param startingDate
     * @param endingDate
     * @return
     */
    @Override
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(Date startingDate, Date endingDate) {
        return jpa.findAllByDateTimeBetween(startingDate, endingDate);
    }
}
