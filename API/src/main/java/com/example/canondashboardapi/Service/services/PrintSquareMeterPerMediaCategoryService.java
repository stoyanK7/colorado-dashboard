package com.example.canondashboardapi.Service.services;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaCategory;
import com.example.canondashboardapi.Repository.interfaces.IGenericRepository;
import com.example.canondashboardapi.Service.interfaces.IPrintSquareMeterPerMediaCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintSquareMeterPerMediaCategoryService implements IPrintSquareMeterPerMediaCategoryService {

    IGenericRepository<PrintSquareMeterPerMediaCategory> printSquareMeterPerMediaRepository;

    @Autowired
    public PrintSquareMeterPerMediaCategoryService(@Qualifier("WorkRepo") IGenericRepository<PrintSquareMeterPerMediaCategory> printSquareMeterPerMediaRepository) {
        this.printSquareMeterPerMediaRepository = printSquareMeterPerMediaRepository;
    }

    public List<PrintSquareMeterPerMediaCategory> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }

    @Override
    public void saveTest(PrintSquareMeterPerMediaCategory testType) {
        printSquareMeterPerMediaRepository.saveTest(testType);
    }
}
