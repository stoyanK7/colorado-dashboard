package com.example.canondashboardapi.Service.interfaces;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaCategory;

import java.util.List;

public interface IPrintSquareMeterPerMediaCategoryService {
    List<PrintSquareMeterPerMediaCategory> getAll();

    void saveTest(PrintSquareMeterPerMediaCategory testType);
}
