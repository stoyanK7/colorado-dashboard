package com.example.canondashboardapi.repository.repositories;

import com.example.canondashboardapi.enumeration.MediaCategory;
import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
import com.example.canondashboardapi.repository.interfaces.GenericRepository;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repository for TotalPrintSquareMeterPerMediaCategoryPerDay model stored as
 * memory objects for testing purposes
 */
@Repository("TestRepo")
public class TotalPrintSquareMeterPerMediaCategoryPerDayRepository implements
        GenericRepository<TotalPrintSquareMeterPerMediaCategoryPerDay> {


    private List<TotalPrintSquareMeterPerMediaCategoryPerDay>
            totalPrintSquareMeterPerMediaCategoryPerDays;

    public TotalPrintSquareMeterPerMediaCategoryPerDayRepository()
            throws ParseException {
        this.totalPrintSquareMeterPerMediaCategoryPerDays = new ArrayList<>();
        String sDate1 = "31/12/1998";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        String sDate2 = "31/12/1999";
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(1, date1, 1,
                        MediaCategory.Film));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(2, date1, 2,
                        MediaCategory.Light_paper));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(3, date1, 3,
                        MediaCategory.Heavy_paper));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(4, date1, 4,
                        MediaCategory.Light_Banner));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(5, date1, 5,
                        MediaCategory.Textile));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(6, date1, 6,
                        MediaCategory.Monomeric_vinyl));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(7, date1, 7,
                        MediaCategory.Canvas));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(8, date1, 8,
                        MediaCategory.Polymeric_and_cast_vinyl));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(9, date1, 9,
                        MediaCategory.Heavy_Banner));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(10, date1, 10,
                        MediaCategory.Paper));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(11, date1, 11,
                        MediaCategory.Thick_film));

        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(12, date2, 1,
                        MediaCategory.Film));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(13, date2, 2,
                        MediaCategory.Light_paper));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(14, date2, 3,
                        MediaCategory.Heavy_paper));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(15, date2, 4,
                        MediaCategory.Light_Banner));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(16, date2, 5,
                        MediaCategory.Textile));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(17, date2, 6,
                        MediaCategory.Monomeric_vinyl));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(18, date2, 7,
                        MediaCategory.Canvas));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(19, date2, 8,
                        MediaCategory.Polymeric_and_cast_vinyl));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(20, date2, 9,
                        MediaCategory.Heavy_Banner));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(21, date2, 10,
                        MediaCategory.Paper));
        this.totalPrintSquareMeterPerMediaCategoryPerDays.add(
                new TotalPrintSquareMeterPerMediaCategoryPerDay(22, date2, 11,
                        MediaCategory.Thick_film));
    }

    /**
     * Gets all objects from the stored list
     * @return
     */
    public List<TotalPrintSquareMeterPerMediaCategoryPerDay> getAll() {
        return this.totalPrintSquareMeterPerMediaCategoryPerDays;
    }

    // TODO: Remove this?
    @Override
    public void saveTest(TotalPrintSquareMeterPerMediaCategoryPerDay testType) {

    }
}
