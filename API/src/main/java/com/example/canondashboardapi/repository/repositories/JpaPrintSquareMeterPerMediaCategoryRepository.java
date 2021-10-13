package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.models.PrintSquareMeterPerMediaCategory;
import com.example.canondashboardapi.repository.interfaces.IGenericRepository;
import com.example.canondashboardapi.repository.interfaces.JpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WorkRepo")
public class JpaPrintSquareMeterPerMediaCategoryRepository implements IGenericRepository<PrintSquareMeterPerMediaCategory> {
    @Autowired
    JpaRepo jpa;
    @Override
    public List<PrintSquareMeterPerMediaCategory> getAll() {
        return jpa.findAll();
    }

    @Override
    public void saveTest(PrintSquareMeterPerMediaCategory testType) {
        jpa.save(testType);
    }
}
