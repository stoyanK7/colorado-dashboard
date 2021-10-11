package com.example.canondashboardapi.Service.services;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import com.example.canondashboardapi.Repository.interfaces.IGenericRepository;
import com.example.canondashboardapi.Service.interfaces.IPrintSquareMeterPerMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintSquareMeterPerMediaTypeTypeService implements IPrintSquareMeterPerMediaTypeService {

    IGenericRepository<PrintSquareMeterPerMediaType> printSquareMeterPerMediaRepository;

    @Autowired
    public PrintSquareMeterPerMediaTypeTypeService(@Qualifier("WorkRepo") IGenericRepository<PrintSquareMeterPerMediaType> printSquareMeterPerMediaRepository) {
        this.printSquareMeterPerMediaRepository = printSquareMeterPerMediaRepository;
    }

    public List<PrintSquareMeterPerMediaType> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }

    @Override
    public void saveTest(PrintSquareMeterPerMediaType testType) {
        printSquareMeterPerMediaRepository.saveTest(testType);
    }
}
