package com.example.canondashboardapi.Converter.interfaces;

import java.util.List;

public interface IGraphConverter <TModel, TDto>{

    TDto modelToDTO(TModel model);

    List<TDto> ListModelToDTO(List<TModel> models);
}
