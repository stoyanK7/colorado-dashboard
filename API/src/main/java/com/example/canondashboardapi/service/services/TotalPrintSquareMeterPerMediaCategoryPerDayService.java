package com.example.canondashboardapi.service.services;


import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents Service for TotalPrintSquareMeterPerMediaCategoryPerDay model.
 */
@Service
public class TotalPrintSquareMeterPerMediaCategoryPerDayService implements
        com.example.canondashboardapi.service.interfaces.TotalPrintSquareMeterPerMediaCategoryPerDayService {

    private GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay>
            printSquareMeterPerMediaRepository;

    @Autowired
    public TotalPrintSquareMeterPerMediaCategoryPerDayService(
            @Qualifier("TestRepo")
                    GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> printSquareMeterPerMediaRepository) {
        this.printSquareMeterPerMediaRepository =
                printSquareMeterPerMediaRepository;
    }

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay model objects
     * stored in repository
     *
     * @return
     */
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }

    // TODO: Remove this?
    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {
        printSquareMeterPerMediaRepository.saveTest(testType);
    }
}
