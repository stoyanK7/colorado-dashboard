package com.example.canondashboardapi.service.services;

import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.IFilterRepository;
import com.example.canondashboardapi.service.interfaces.IFiltrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FiltrationService implements IFiltrationService {

    @Autowired
    IFilterRepository repo;

    @Override
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(Date startingDate, Date endingDate) {
        return repo.getDataByDates(startingDate, endingDate);
    }
}
