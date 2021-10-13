package com.example.canondashboardapi.service.services;


import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.IGenericRepository;
import com.example.canondashboardapi.service.interfaces.ITotalPrintSquareMeterPerMediaCategoryPerDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintSquareMeterPerMediaCategoryService implements ITotalPrintSquareMeterPerMediaCategoryPerDayService {

    IGenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> printSquareMeterPerMediaRepository;

    @Autowired
    public PrintSquareMeterPerMediaCategoryService(@Qualifier("TestRepo") IGenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> printSquareMeterPerMediaRepository) {
        this.printSquareMeterPerMediaRepository = printSquareMeterPerMediaRepository;
    }

    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }

    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {
        printSquareMeterPerMediaRepository.saveTest(testType);
    }
}
