package com.example.canondashboardapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// TODO: Need to change later
@RequestMapping("PrintSquareMeterPerMediaType")
// TODO: Need to change later
@CrossOrigin("http://localhost:3000")
public class PrintSquareMeterPerMediaTypeController {

    @Autowired
    IGraphConverter graphConverter;

    @Autowired
    IPrintSquareMeterPerMediaService printSquareMeterPerMediaService;

    @GetMapping()
    public ResponseEntity<List<PrintSquareMeterPerMediaTypeDTO>> getAll(){
        List<PrintSquareMeterPerMediaTypeDTO> printSquareMeterPerMediaTypeDTOList = graphConverter.entityToDto(printSquareMeterPerMediaService.getAll());
        if (printSquareMeterPerMediaTypeDTOList != null){
            return ResponseEntity.ok().body(printSquareMeterPerMediaTypeDTOList);
        }else{
            return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
        }
    }
}
