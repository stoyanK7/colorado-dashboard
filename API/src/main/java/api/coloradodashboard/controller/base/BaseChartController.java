package api.coloradodashboard.controller.base;

import api.coloradodashboard.entity.base.BaseEntity;
import api.coloradodashboard.service.base.BaseService;
import api.coloradodashboard.dto.ChartDataKeysDto;
import api.coloradodashboard.dto.PeriodAndPrinterIdsDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.dto.PrinterIdsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Base controller that defines the endpoints each chart controller should have.
 *
 * @param <E> Entity
 * @param <D> Dto
 */
@AllArgsConstructor
public class BaseChartController<E extends BaseEntity, D>
        implements BaseController {
    private BaseService<E, D> service;

    /**
     * Retrieves all data for all time.
     *
     * @param aggregated true or false. Defines whether the data should be summarized
     *                   for all printers or separate for each one.
     * @param bin        day or week. Defines whether the data should be summarized by days
     *                   or by weeks.
     * @return List of DTOs
     */
    @PostMapping
    public ResponseEntity<List<D>> getAll(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                          @RequestParam(value = "bin", defaultValue = "day") String bin) {
        return createResponse(service.getAll(aggregated, bin));
    }

    /**
     * Retrieves all data for a given period of interest.
     *
     * @param aggregated true or false. Defines whether the data should be summarized
     *                   for all printers or separate for each one.
     * @param bin        day or week. Defines whether the data should be summarized by days
     *                   or by weeks.
     * @return List of DTOs
     */
    @PostMapping("/Period")
    public ResponseEntity<List<D>> getAllForPeriod(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                                   @RequestParam(value = "bin", defaultValue = "day") String bin,
                                                   @RequestBody PeriodDto request) {
        return createResponse(service.getAllForPeriod(aggregated, bin, request.getFrom(), request.getTo()));
    }

    /**
     * Retrieves data for certain printers for all time.
     *
     * @param aggregated true or false. Defines whether the data should be summarized
     *                   for all printers or separate for each one.
     * @param bin        day or week. Defines whether the data should be summarized by days
     *                   or by weeks.
     * @return List of DTOs
     */
    @PostMapping("/Printers")
    public ResponseEntity<List<D>> getAllForPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                                     @RequestParam(value = "bin", defaultValue = "day") String bin,
                                                     @RequestBody PrinterIdsDto request) {
        return createResponse(service.getAllForPrinters(aggregated, bin, request.getPrinterIds()));
    }

    /**
     * Retrieves data for certain printers for given period of interest.
     *
     * @param aggregated true or false. Defines whether the data should be summarized
     *                   for all printers or separate for each one.
     * @param bin        day or week. Defines whether the data should be summarized by days
     *                   or by weeks.
     * @return List of DTOs
     */
    @PostMapping("/PeriodAndPrinters")
    public ResponseEntity<List<D>> getAllForPeriodAndPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                                              @RequestParam(value = "bin", defaultValue = "day") String bin,
                                                              @RequestBody PeriodAndPrinterIdsDto request) {
        return createResponse(service.getAllForPeriodAndPrinters(aggregated, bin, request.getFrom(), request.getTo(), request.getPrinterIds()));
    }

    /**
     * Retrieves the minimum and maximum date in the database table.
     *
     * @return PeriodDto object containing 'from' and 'to' fields, representing
     * the earliest and latest date.
     */
    @GetMapping("/AvailableTimePeriod")
    public ResponseEntity<PeriodDto> getAvailableTimePeriod() {
        return createResponse(service.getAvailableTimePeriod());
    }

    /**
     * Retrieves a list of all printers in the database table.
     *
     * @return PrinterIdsDto containing an array of the printers.
     */
    @GetMapping("/AvailablePrinters")
    public ResponseEntity<PrinterIdsDto> getAvailablePrinters() {
        return createResponse(service.getAvailablePrinters());
    }

    /**
     * Retrieves a list of the chart data keys that Recharts requires in order
     * to visualize the chart. Not all charts might need this method, but most do.
     *
     * @return ChartDataKeysDto containing an array of the keys.
     */
    @GetMapping("/ChartDataKeys")
    public ResponseEntity<ChartDataKeysDto> getChartDataKeys() {
        return createResponse(service.getChartDataKeys());
    }
}
