package com.example.canondashboardapi.controller;


import com.example.canondashboardapi.converter.interfaces.IGraphConverter;
import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.service.interfaces.ITotalPrintSquareMeterPerMediaCategoryPerDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
// TODO: Need to change later
@RequestMapping("PrintSquareMeterPerMediaType")
// TODO: Need to change later
@CrossOrigin("http://localhost:4000")
public class PrintSquareMeterPerMediaCategoryController {

    @Autowired
    IGraphConverter<List<TotalPrintSquareMeterPerMediaCategoryPerDay>,List<Map<String, String>>> graphConverter;

    @Autowired
    ITotalPrintSquareMeterPerMediaCategoryPerDayService printSquareMeterPerMediaCategoryPerDayService;


    @GetMapping()
    // change to TotalPrintSquareMeterPerMediaCategoryPerDay
    public ResponseEntity<List<Map<String, String>>> getAll(){
        List<Map<String, String>> graphDayBars = graphConverter.modelToDTO(printSquareMeterPerMediaCategoryPerDayService.getAll());
        if (graphDayBars != null){
            return ResponseEntity.ok().body(graphDayBars);
        }else{
            return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveTest")
    public void saveTest(@RequestBody TotalPrintSquareMeterPerMediaCategoryPerDay testType){
//        List<PrintSquareMeterPerMediaTypeDTO> printSquareMeterPerMediaTypeDTOList = graphConverter.entityToDto(printSquareMeterPerMediaService.getAll());
//        if (printSquareMeterPerMediaTypeDTOList != null){
//            return ResponseEntity.ok().body(printSquareMeterPerMediaTypeDTOList);
//        }else{
//            return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
//        }
        printSquareMeterPerMediaCategoryPerDayService.saveTest(testType);
    }
}
