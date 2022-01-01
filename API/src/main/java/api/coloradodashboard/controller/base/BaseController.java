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

@AllArgsConstructor
public class BaseController<T> {
    private BaseService<T> service;

    @PostMapping
    public ResponseEntity<List<T>> getAll(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                          @RequestParam(value = "bin", defaultValue = "day") String bin) {
        return createResponse(service.getAll(aggregated, bin));
    }

    @PostMapping("/Period")
    public ResponseEntity<List<T>> getAllForPeriod(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                                   @RequestParam(value = "bin", defaultValue = "day") String bin,
                                                   @RequestBody PeriodDto request) {
        return createResponse(service.getAllForPeriod(aggregated, bin, request.getFrom(), request.getTo()));
    }

    @PostMapping("/Printer")
    public ResponseEntity<List<T>> getAllForPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                                     @RequestParam(value = "bin", defaultValue = "day") String bin,
                                                     @RequestBody PrinterIdsDto request) {
        return createResponse(service.getAllForPrinters(aggregated, bin, request.getPrinterIds()));
    }

    @PostMapping("/PeriodAndPrinters")
    public ResponseEntity<List<T>> getAllForPeriodAndPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated,
                                                              @RequestParam(value = "bin", defaultValue = "day") String bin,
                                                              @RequestBody PeriodAndPrinterIdsDto request) {
        return createResponse(service.getAllForPeriodAndPrinters(aggregated, bin, request.getFrom(), request.getTo(), request.getPrinterIds()));
    }

    @GetMapping("/AvailableTimePeriod")
    public ResponseEntity<PeriodDto> getAvailableTimePeriod() {
        return createResponse(service.getAvailableTimePeriod());
    }

    @GetMapping("/AvailablePrinters")
    public ResponseEntity<PrinterIdsDto> getAvailablePrinters() {
        return createResponse(service.getAvailablePrinters());
    }

    @GetMapping("/ChartDataKeys")
    public ResponseEntity<ChartDataKeysDto> getChartDataKeys() {
        return createResponse(service.getChartDataKeys());
    }

    private ResponseEntity<List<T>> createResponse(List<T> data) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    private <Y> ResponseEntity<Y> createResponse(Y data) {
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
