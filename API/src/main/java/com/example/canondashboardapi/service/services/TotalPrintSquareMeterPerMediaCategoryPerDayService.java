package com.example.canondashboardapi.service.services;


import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.service.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents Service for TotalPrintSquareMeterPerMediaCategoryPerDay model.
 */
@Service
@AllArgsConstructor
public class TotalPrintSquareMeterPerMediaCategoryPerDayService implements
        GenericService<TotalPrintSquareMeterPerMediaCategoryPerDay> {

    private GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> repository;

    /**
     * Gets all TotalPrintSquareMeterPerMediaCategoryPerDay model objects
     * stored in repository
     *
     * @return
     */
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return repository.getAll();
    }
}
