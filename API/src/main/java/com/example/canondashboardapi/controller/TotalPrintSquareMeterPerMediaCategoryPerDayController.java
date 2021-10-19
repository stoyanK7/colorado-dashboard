package com.example.canondashboardapi.controller;


import com.example.canondashboardapi.converter.interfaces.GenericGraphConverter;
import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.service.interfaces.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST api controller for TotalPrintSquareMeterPerMediaCategoryPerDay. Outgoing graph
 * information is output as a List of Maps, where very Map represents a bar
 * in the graph.
 */
@RestController
// TODO: Need to change later
@RequestMapping("PrintSquareMeterPerMediaType")
// TODO: Need to change later
@CrossOrigin("http://localhost:4000")
public class TotalPrintSquareMeterPerMediaCategoryPerDayController {

    @Autowired
    GenericGraphConverter<List<TotalPrintSquareMeterPerMediaCategoryPerDay>, List<Map<String, String>>>
            graphConverter;

    @Autowired
    GenericService<TotalPrintSquareMeterPerMediaCategoryPerDay>
            totalPrintSquareMeterPerMediaCategoryPerDayService;

    /**
     * GET request that returns all the data stored in the repository
     * @return A List of Maps, representing all the days stored in the
     * repository
     */
    @GetMapping()
    public ResponseEntity<List<Map<String, String>>> getAll() {
        List<Map<String, String>> graphDayBars = graphConverter.modelToDTO(
                totalPrintSquareMeterPerMediaCategoryPerDayService.getAll());
        if (graphDayBars != null) {
            return ResponseEntity.ok().body(graphDayBars);
        } else {
            return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getTest")
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getTest(){
        return totalPrintSquareMeterPerMediaCategoryPerDayService.getAll();
    }

    @PostMapping("/saveTest")
    public void saveTest(@RequestBody TotalPrintSquareMeterPerMediaCategoryPerDay testType){
        totalPrintSquareMeterPerMediaCategoryPerDayService.saveTest(testType);
    }
}
