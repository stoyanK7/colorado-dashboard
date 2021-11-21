package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.repository.interfaces.JpaTotalPrintSquareMeterPerMediaCategoryPerDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for TotalPrintSquareMeterPerMediaCategoryPerDay model connected
 * to JPA database
 */
@Repository("WorkRepo")
public class JpaTotalPrintSquareMeterPerMediaCategoryPerDayRepository implements
        GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> {
    @Autowired
    private JpaTotalPrintSquareMeterPerMediaCategoryPerDay jpa;

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay models from the
     * database
     * @return
     */
    @Override
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return jpa.findAll();
    }
}
