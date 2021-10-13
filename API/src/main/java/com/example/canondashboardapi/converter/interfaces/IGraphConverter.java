package com.example.canondashboardapi.converter.interfaces;

import java.util.List;

public interface IGraphConverter <TModel, TDto>{

    TDto modelToDTO(TModel model);

    List<TDto> ListModelToDTO(List<TModel> models);
}
