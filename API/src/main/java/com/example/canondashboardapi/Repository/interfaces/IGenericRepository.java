package com.example.canondashboardapi.Repository.interfaces;

import java.util.List;

public interface IGenericRepository<TModel> {
    List<TModel> getAll();

    void saveTest(TModel testType);
}
