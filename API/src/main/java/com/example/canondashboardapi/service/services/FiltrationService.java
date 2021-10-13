package com.example.canondashboardapi.service.services;

import com.example.canondashboardapi.repository.interfaces.IfilterRepository;
import com.example.canondashboardapi.service.interfaces.IFiltrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FiltrationService implements IFiltrationService {

    @Autowired
    IfilterRepository repo;

    @Override
    public void getDataByDates(Date startingDate, Date endingDate) {
        repo.getDataByDates(startingDate, endingDate);
    }
}
