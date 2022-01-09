package api.coloradodashboard.controller.base;

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
 * Base controller that defines the endpoints each controller should have.
 *
 * @param <T> DTO that the controller returns.
 */
@AllArgsConstructor
public abstract class BaseController<T> {
    private BaseService<T> service;

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
    public ResponseEntity<List<T>> getAll(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
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
    public ResponseEntity<List<T>> getAllForPeriod(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
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
    public ResponseEntity<List<T>> getAllForPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
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
    public ResponseEntity<List<T>> getAllForPeriodAndPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
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

    /**
     * Creates a response entity based on whether the provided list is empty or not.
     *
     * @param data List of DTOs
     * @return ResponseEntity with status code 200/OK and the list of DTOs or 404/
     * NOT FOUND if the data list is empty.
     */
    private ResponseEntity<List<T>> createResponse(List<T> data) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * Creates a response entity based on whether the provided object is null or not.
     *
     * @param data Any object
     * @param <Y>  Any class. Usually PeriodDto, PrinterIdsDto or ChartDataKeysDto
     *             but any class can be used here.
     * @return ResponseEntity with status code 200/OK and the object or 404/NOT FOUND
     * if the object is null.
     */
    private <Y> ResponseEntity<Y> createResponse(Y data) {
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
