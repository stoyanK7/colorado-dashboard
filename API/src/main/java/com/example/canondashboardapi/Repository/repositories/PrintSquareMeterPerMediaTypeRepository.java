package com.example.canondashboardapi.Repository.repositories;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import com.example.canondashboardapi.Repository.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PrintSquareMeterPerMediaTypeRepository implements IGenericRepository<PrintSquareMeterPerMediaType> {


    private List<PrintSquareMeterPerMediaType> printSquareMeterPerMediaTypeList;

    public PrintSquareMeterPerMediaTypeRepository(){
        this.printSquareMeterPerMediaTypeList = new ArrayList<>();
    }

    public List<PrintSquareMeterPerMediaType> getAll() {
        return this.printSquareMeterPerMediaTypeList;
    }
}
