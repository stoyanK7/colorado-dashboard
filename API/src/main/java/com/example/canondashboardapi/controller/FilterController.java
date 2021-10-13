package com.example.canondashboardapi.controller;

import com.example.canondashboardapi.service.interfaces.IFiltrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("filtration")
@CrossOrigin("http://localhost:4000")
public class FilterController {
    @Autowired
    IFiltrationService service;
    
    @GetMapping("byDates")
    public void getDataByDates(@RequestBody demoClass){
        
        service.getDataByDates(demoClass.getStartingDate(), demoClass.getEndingDate());
    }
}
