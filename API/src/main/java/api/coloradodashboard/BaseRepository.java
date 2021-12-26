package api.coloradodashboard;

import java.util.Date;
import java.util.List;

public interface BaseRepository<T> {
    List<T> getAllAggregated();

    List<T> getAllNonAggregated();

    List<T> getAllForPeriodAggregated(Date from, Date to);

    List<T> getAllForPeriodNonAggregated(Date from, Date to);

    List<T> getAllForPrintersAggregated(List<String> printerIds);

    List<T> getAllForPrintersNonAggregated(List<String> printerIds);

    List<T> getAllForPeriodAndPrintersAggregated(Date from, Date to, List<String> printerIds);

    List<T> getAllForPeriodAndPrintersNonAggregated(Date from, Date to, List<String> printerIds);

    PeriodDto getAvailableTimePeriod();

    List<String> getAvailablePrinters();
}
