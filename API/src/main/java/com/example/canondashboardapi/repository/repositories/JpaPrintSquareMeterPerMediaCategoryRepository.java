package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.IGenericRepository;
import com.example.canondashboardapi.repository.interfaces.JpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WorkRepo")
public class JpaPrintSquareMeterPerMediaCategoryRepository implements IGenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> {
    @Autowired
    JpaRepo jpa;
    @Override
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return jpa.findAll();
    }

    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {
        jpa.save(testType);
    }
}
