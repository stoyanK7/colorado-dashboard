package com.example.canondashboardapi.service.services;

import com.example.canondashboardapi.model.InkUsagePerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.service.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for InkUsagePerDay model.
 */
@Service
@AllArgsConstructor
public class InkUsagePerDayService implements GenericService<InkUsagePerDay> {
    private GenericRepository<InkUsagePerDay> repository;

    /**
     * Retrieves all InkUsagePerDay model objects.
     *
     * @return
     */
    public List<InkUsagePerDay> getAll() {
        return repository.getAll();
    }
}
