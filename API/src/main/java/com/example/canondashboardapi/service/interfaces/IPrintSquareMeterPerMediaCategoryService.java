package com.example.canondashboardapi.service.interfaces;

import com.example.canondashboardapi.model.models.PrintSquareMeterPerMediaCategory;

import java.util.List;

public interface IPrintSquareMeterPerMediaCategoryService {
    List<PrintSquareMeterPerMediaCategory> getAll();

    void saveTest(PrintSquareMeterPerMediaCategory testType);
}
