package com.example.canondashboardapi.converter.interfaces;

import java.util.List;

/**
 * Generic converter that converts models to a data format readable by a graph
 * @param <TModel> Model object
 * @param <TDto> Data Transfer object
 */
public interface GenericGraphConverter<TModel, TDto>{
    /**
     * Converts a model object to its respective DTO, as readable by a graph
     * @param model
     * @return
     */
    TDto modelToDTO(TModel model);
}
