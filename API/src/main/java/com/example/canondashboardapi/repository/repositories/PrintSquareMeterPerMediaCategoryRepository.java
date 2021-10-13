package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.model.models.PrintSquareMeterPerMediaCategory;
import com.example.canondashboardapi.repository.interfaces.IGenericRepository;
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
