package com.example.canondashboardapi.converter.interfaces;

/**
 * Generic converter that converts models to a data format readable by a graph.
 *
 * @param <TModel> Model object
 * @param <TDto>   Data Transfer object
 */
public interface GenericGraphConverter<TModel, TDto> {
    /**
     * Converts a model object to its respective DTO, as readable by a graph.
     *
     * @param model Model object.
     * @return TDto Data transfer object, which Nivo turns into a chart.
     */
    TDto modelToDTO(TModel model);
}
