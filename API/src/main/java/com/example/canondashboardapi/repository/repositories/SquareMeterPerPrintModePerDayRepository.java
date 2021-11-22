package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.SquareMeterPerPrintModePerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import com.example.canondashboardapi.repository.interfaces.JpaSquareMeterPerPrintModePerDayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SquareMeterPerPrintModePerDay model.
 */
@Repository
@AllArgsConstructor
public class SquareMeterPerPrintModePerDayRepository implements GenericRepository<SquareMeterPerPrintModePerDay> {
    private JpaSquareMeterPerPrintModePerDayRepository jpa;

    /**
     * Retrieves all SquareMeterPerPrintModePerDay models from the database.
     *
     * @return
     */
    @Override
    public List<SquareMeterPerPrintModePerDay> getAll() {
        return jpa.findAll();
    }
}
