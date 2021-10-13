package com.example.canondashboardapi.repository.interfaces;

import java.util.List;

public interface IGenericRepository<TModel> {
    List<TModel> getAll();

    void saveTest(TModel testType);
}
