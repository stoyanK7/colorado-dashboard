package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.InkUsagePerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.repository.interfaces.JpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for InkUsagePerDay model.
 */
@Repository
@AllArgsConstructor
public class InkUsagePerDayRepository implements GenericRepository<InkUsagePerDay> {
    private JpaRepository<InkUsagePerDay> jpa;

    /**
     * Retrieves all InkUsagePerDay models from the database.
     *
     * @return
     */
    @Override
    public List<InkUsagePerDay> getAll() {
        return jpa.findAll();
    }
}
