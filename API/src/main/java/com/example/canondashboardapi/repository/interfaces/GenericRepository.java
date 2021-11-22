package com.example.canondashboardapi.repository.interfaces;

import java.util.List;

/**
 * Represents the generic CRUD functionality of a repository
 *
 * @param <TModel> Model that the CRUD operations should apply to
 */
public interface GenericRepository<TModel> {
    /**
     * Get all objects of this model
     *
     * @return List of all models that are stored
     */
    List<TModel> getAll();
}
