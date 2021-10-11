package com.example.canondashboardapi.Controller;

import com.example.canondashboardapi.DTO.PrintSquareMeterPerMediaTypeDTO;
import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import com.example.canondashboardapi.Service.interfaces.IPrintSquareMeterPerMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// TODO: Need to change later
@RequestMapping("PrintSquareMeterPerMediaType")
// TODO: Need to change later
@CrossOrigin("http://localhost:3000")
public class PrintSquareMeterPerMediaTypeController {

//    @Autowired
//    IGraphConverter graphConverter;

    @Autowired
    IPrintSquareMeterPerMediaTypeService printSquareMeterPerMediaService;

    @GetMapping()
    public ResponseEntity<List<PrintSquareMeterPerMediaType>> getAll(){
//        List<PrintSquareMeterPerMediaTypeDTO> printSquareMeterPerMediaTypeDTOList = graphConverter.entityToDto(printSquareMeterPerMediaService.getAll());
//        if (printSquareMeterPerMediaTypeDTOList != null){
//            return ResponseEntity.ok().body(printSquareMeterPerMediaTypeDTOList);
//        }else{
//            return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
//        }
        return ResponseEntity.ok().body(printSquareMeterPerMediaService.getAll());
    }

    @PostMapping("/saveTest")
    public void saveTest(@RequestBody PrintSquareMeterPerMediaType testType){
//        List<PrintSquareMeterPerMediaTypeDTO> printSquareMeterPerMediaTypeDTOList = graphConverter.entityToDto(printSquareMeterPerMediaService.getAll());
//        if (printSquareMeterPerMediaTypeDTOList != null){
//            return ResponseEntity.ok().body(printSquareMeterPerMediaTypeDTOList);
//        }else{
//            return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
//        }
        printSquareMeterPerMediaService.saveTest(testType);
    }
}
