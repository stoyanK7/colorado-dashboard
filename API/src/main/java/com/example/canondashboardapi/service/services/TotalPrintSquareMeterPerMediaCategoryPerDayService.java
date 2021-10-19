package com.example.canondashboardapi.service.services;


import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.service.interfaces.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents Service for TotalPrintSquareMeterPerMediaCategoryPerDay model.
 */
@Service
public class TotalPrintSquareMeterPerMediaCategoryPerDayService implements
        GenericService<TotalPrintSquareMeterPerMediaCategoryPerDay> {

    private GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay>
            totalPrintSquareMeterPerMediaCategoryPerDayRepository;

    @Autowired
    public TotalPrintSquareMeterPerMediaCategoryPerDayService(
            @Qualifier("WorkRepo")
                    GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> totalPrintSquareMeterPerMediaCategoryPerDayRepository) {
        this.totalPrintSquareMeterPerMediaCategoryPerDayRepository =
                totalPrintSquareMeterPerMediaCategoryPerDayRepository;
    }

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay model objects
     * stored in repository
     *
     * @return
     */
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return totalPrintSquareMeterPerMediaCategoryPerDayRepository.getAll();
    }

    // TODO: Remove this?
    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {
        totalPrintSquareMeterPerMediaCategoryPerDayRepository.saveTest(testType);
    }
}
