package com.example.canondashboardapi.Service.services;

import com.example.canondashboardapi.Service.interfaces.IPrintSquareMeterPerMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintSquareMeterPerMediaTypeTypeService implements IPrintSquareMeterPerMediaTypeService {

    @Autowired
    IGenericRepository<PrintSquareMeterPerMedia> printSquareMeterPerMediaRepository;

    public List<PrintSquareMeterPerMedia> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }
}
