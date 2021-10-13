package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("TestRepo")
public class PrintSquareMeterPerMediaCategoryRepository implements IGenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> {


    private List<TotalPrintSquareMeterPerMediaCategoryPerDay> totalPrintSquareMeterPerMediaCategoryPerDays;

    public PrintSquareMeterPerMediaCategoryRepository(){
        this.totalPrintSquareMeterPerMediaCategoryPerDays = new ArrayList<>();
    }

    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return this.totalPrintSquareMeterPerMediaCategoryPerDays;
    }

    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {

    }
}
