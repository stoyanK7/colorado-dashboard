package api.coloradodashboard.service.base;

import api.coloradodashboard.dto.ChartDataKeysDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.dto.PrinterIdsDto;
import api.coloradodashboard.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Date;
import java.util.List;

public class BaseService<T> {
    private final BaseRepository<T> repository;

    public BaseService(@Lazy @Autowired BaseRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> getAll(boolean aggregated, String bin) {
        String dateFormat = dateFormatSwitch(bin);
        return aggregated ? repository.getAllAggregated(dateFormat)
                : repository.getAllNonAggregated(dateFormat);
    }

    public List<T> getAllForPeriod(boolean aggregated, String bin, Date from, Date to) {
        String dateFormat = dateFormatSwitch(bin);
        return aggregated ? repository.getAllForPeriodAggregated(dateFormat, from, to)
                : repository.getAllForPeriodNonAggregated(dateFormat, from, to);
    }

    public List<T> getAllForPrinters(boolean aggregated, String bin, List<String> printerIds) {
        String dateFormat = dateFormatSwitch(bin);
        return aggregated ? repository.getAllForPrintersAggregated(dateFormat, printerIds)
                : repository.getAllForPrintersNonAggregated(dateFormat, printerIds);
    }

    public List<T> getAllForPeriodAndPrinters(boolean aggregated, String bin, Date from, Date to, List<String> printerIds) {
        String dateFormat = dateFormatSwitch(bin);
        return aggregated ? repository.getAllForPeriodAndPrintersAggregated(dateFormat, from, to, printerIds)
                : repository.getAllForPeriodAndPrintersNonAggregated(dateFormat, from, to, printerIds);
    }

    public PeriodDto getAvailableTimePeriod() {
        return repository.getAvailableTimePeriod();
    }

    public PrinterIdsDto getAvailablePrinters() {
        return new PrinterIdsDto(repository.getAvailablePrinters());
    }

    public ChartDataKeysDto getChartDataKeys() {
        return new ChartDataKeysDto(repository.getChartDataKeys());
    }

    private String dateFormatSwitch(String bin) {
        String dateFormat = "%Y/%b/%d"; // 2021/Dec/22 - day
        switch (bin) {
            case "week":
                dateFormat = "%Y/%u"; // 2021/42 - week
                break;
        }
        return dateFormat;
    }
}
