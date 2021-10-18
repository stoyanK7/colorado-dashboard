package com.example.canondashboardapi.controller;

import com.example.canondashboardapi.model.models.DatesModel;
import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.service.interfaces.IFiltrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("filtration")
@CrossOrigin("http://localhost:4000")
public class FilterController {
    @Autowired
    IFiltrationService service;

    /**
     * Retrieves data for the timeframe
     * @param model
     * @return
     */
    @GetMapping("byDates")
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getDataByDates(@RequestBody DatesModel model){
        
        return service.getDataByDates(model.getStartingDate(), model.getEndingDate());
    }
}
