package com.example.canondashboardapi.service.interfaces;

import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents the generic CRUD functionality of a service
 * @param <TModel> Model that the CRUD operations should apply to
 */
@Service
public interface GenericService<TModel> {
    /**
     * Get all objects of this model
     * @return List of all models that are stored
     */
    List<TModel> getAll();

    void saveTest(TModel testType);
}
