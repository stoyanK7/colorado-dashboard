package com.example.canondashboardapi.Service.services;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import com.example.canondashboardapi.Service.interfaces.IPrintSquareMeterPerMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintSquareMeterPerMediaTypeTypeService implements IPrintSquareMeterPerMediaTypeService {

    @Autowired
    IGenericRepository<PrintSquareMeterPerMediaType> printSquareMeterPerMediaRepository;

    public List<PrintSquareMeterPerMediaType> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }
}
