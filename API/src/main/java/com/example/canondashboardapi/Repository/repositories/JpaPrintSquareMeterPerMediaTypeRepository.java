package com.example.canondashboardapi.Repository.repositories;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import com.example.canondashboardapi.Repository.interfaces.IGenericRepository;
import com.example.canondashboardapi.Repository.interfaces.JpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WorkRepo")
public class JpaPrintSquareMeterPerMediaTypeRepository implements IGenericRepository<PrintSquareMeterPerMediaType> {
    @Autowired
    JpaRepo jpa;
    @Override
    public List<PrintSquareMeterPerMediaType> getAll() {
        return jpa.findAll();
    }

    @Override
    public void saveTest(PrintSquareMeterPerMediaType testType) {
        jpa.save(testType);
    }
}
