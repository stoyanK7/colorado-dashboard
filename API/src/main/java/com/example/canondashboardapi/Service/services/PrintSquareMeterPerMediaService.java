package com.example.canondashboardapi.Service.services;

import com.example.canondashboardapi.Service.interfaces.IPrintSquareMeterPerMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintSquareMeterPerMediaService implements IPrintSquareMeterPerMediaService {

    @Autowired
    IGenericRepository<PrintSquareMeterPerMedia> printSquareMeterPerMediaRepository;

    public List<PrintSquareMeterPerMedia> getAll() {
        return printSquareMeterPerMediaRepository.getAll();
    }
}
