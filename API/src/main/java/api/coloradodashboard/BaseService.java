package api.coloradodashboard;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class BaseService<T> {
    private BaseRepository<T> repository;

    public List<T> getAll(boolean aggregated, String bin) {
        return aggregated ? repository.getAllAggregated() : repository.getAllNonAggregated();
    }

    public List<T> getAllForPeriod(boolean aggregated, Date from, Date to) {
        return aggregated ? repository.getAllForPeriodAggregated(from, to)
                : repository.getAllForPeriodNonAggregated(from, to);
    }

    public List<T> getAllForPrinters(boolean aggregated, List<String> printerIds) {
        return aggregated ? repository.getAllForPrintersAggregated(printerIds)
                : repository.getAllForPrintersNonAggregated(printerIds);
    }

    public List<T> getAllForPeriodAndPrinters(boolean aggregated, Date from, Date to, List<String> printerIds) {
        return aggregated ? repository.getAllForPeriodAndPrintersAggregated(from, to, printerIds)
                : repository.getAllForPeriodAndPrintersNonAggregated(from, to, printerIds);
    }

    public PeriodDto getAvailableTimePeriod() {
        return repository.getAvailableTimePeriod();
    }

    public PrinterIdsDto getAvailablePrinters() {
        return new PrinterIdsDto(repository.getAvailablePrinters());
    }
}
