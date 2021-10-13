package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.enumeration.MediaCategory;
import com.example.canondashboardapi.model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("TestRepo")
public class PrintSquareMeterPerMediaCategoryRepository implements IGenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> {


    private List<TotalPrintSquareMeterPerMediaCategoryPerDay> totalPrintSquareMeterPerMediaCategoryPerDays;

    public PrintSquareMeterPerMediaCategoryRepository() throws ParseException {
        this.totalPrintSquareMeterPerMediaCategoryPerDays = new ArrayList<>();
        String sDate1="31/12/1998";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        TotalPrintSquareMeterPerMediaCategoryPerDay totalPrintSquareMeterPerMediaCategoryPerDay1 = new TotalPrintSquareMeterPerMediaCategoryPerDay(1, date1, 1, MediaCategory.Canvas);
        TotalPrintSquareMeterPerMediaCategoryPerDay totalPrintSquareMeterPerMediaCategoryPerDay2 = new TotalPrintSquareMeterPerMediaCategoryPerDay(2, date1, 1, MediaCategory.Thick_film);
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(totalPrintSquareMeterPerMediaCategoryPerDay1);
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(totalPrintSquareMeterPerMediaCategoryPerDay2);
    }

    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return this.totalPrintSquareMeterPerMediaCategoryPerDays;
    }

    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {

    }
}
