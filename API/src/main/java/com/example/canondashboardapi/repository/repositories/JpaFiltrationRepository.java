package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.repository.interfaces.IfilterRepository;
import com.example.canondashboardapi.repository.interfaces.JpaFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class JpaFiltrationRepository implements IfilterRepository {

    @Autowired
    JpaFilter jpa;

    @Override
    public void getDataByDates(Date startingDate, Date endingDate) {
        jpa.findAll();
    }
}
