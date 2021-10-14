package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.repository.interfaces.JpaRepo;
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
    private JpaRepo jpa;

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay models from the
     * database
     * @return
     */
    @Override
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return jpa.findAll();
    }

    // TODO: Remove this?
    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {
        jpa.save(testType);
    }
}
