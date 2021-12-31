package api.coloradodashboard.repository;


import api.coloradodashboard.dto.PeriodDto;

import java.util.Date;
import java.util.List;

public interface BaseRepository<T> {
    List<T> getAllAggregated(String dateFormat);

    List<T> getAllNonAggregated(String dateFormat);

    List<T> getAllForPeriodAggregated(String dateFormat, Date from, Date to);

    List<T> getAllForPeriodNonAggregated(String dateFormat, Date from, Date to);

    List<T> getAllForPrintersAggregated(String dateFormat, List<String> printerIds);

    List<T> getAllForPrintersNonAggregated(String dateFormat, List<String> printerIds);

    List<T> getAllForPeriodAndPrintersAggregated(String dateFormat, Date from, Date to, List<String> printerIds);

    List<T> getAllForPeriodAndPrintersNonAggregated(String dateFormat, Date from, Date to, List<String> printerIds);

    PeriodDto getAvailableTimePeriod();

    List<String> getAvailablePrinters();

    List<String> getChartDataKeys();
}
