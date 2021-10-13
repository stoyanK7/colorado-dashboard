package com.example.canondashboardapi.repository.interfaces;

import java.util.Date;

public interface IfilterRepository {

    void getDataByDates(Date startingDate, Date endingDate);
}
