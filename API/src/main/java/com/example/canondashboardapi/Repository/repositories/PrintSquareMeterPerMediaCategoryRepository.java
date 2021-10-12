package com.example.canondashboardapi.Repository.repositories;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaCategory;
import com.example.canondashboardapi.Repository.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("TestRepo")
public class PrintSquareMeterPerMediaCategoryRepository implements IGenericRepository<PrintSquareMeterPerMediaCategory> {


    private List<PrintSquareMeterPerMediaCategory> printSquareMeterPerMediaTypeList;

    public PrintSquareMeterPerMediaCategoryRepository(){
        this.printSquareMeterPerMediaTypeList = new ArrayList<>();
    }

    public List<PrintSquareMeterPerMediaCategory> getAll() {
        return this.printSquareMeterPerMediaTypeList;
    }

    @Override
    public void saveTest(PrintSquareMeterPerMediaCategory testType) {

    }
}
