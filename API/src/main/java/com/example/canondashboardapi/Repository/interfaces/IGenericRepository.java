package com.example.canondashboardapi.Repository.interfaces;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;

import java.util.List;

public interface IGenericRepository<TModel> {
    List<TModel> getAll();

    void saveTest(TModel testType);
}
