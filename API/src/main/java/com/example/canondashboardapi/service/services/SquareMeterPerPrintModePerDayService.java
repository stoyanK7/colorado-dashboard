package com.example.canondashboardapi.service.services;

import com.example.canondashboardapi.model.SquareMeterPerPrintModePerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.service.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for SquareMeterPerPrintModePerDay model.
 */
@Service
@AllArgsConstructor
public class SquareMeterPerPrintModePerDayService implements GenericService<SquareMeterPerPrintModePerDay> {
    private GenericRepository<SquareMeterPerPrintModePerDay> repository;

    /**
     * Retrieves all SquareMeterPerPrintModePerDay model objects.
     *
     * @return
     */
    public List<SquareMeterPerPrintModePerDay> getAll() {
        return repository.getAll();
    }
}
