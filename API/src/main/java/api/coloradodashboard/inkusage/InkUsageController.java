package api.coloradodashboard.inkusage;

import api.coloradodashboard.PeriodAndPrinterIdsDto;
import api.coloradodashboard.PeriodDto;
import api.coloradodashboard.PrinterIdsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>REST API</b> controller for <b><i>Ink usage</i></b> chart.
 * Returns a list of <b>InkUsageAggregatedDto</b>/<b>InkUsageNonAggregatedDto</b> objects or <b>404</b> if no data is present.
 */
@RestController
@RequestMapping("InkUsage")
@CrossOrigin("http://localhost:4000")
@AllArgsConstructor
public class InkUsageController {
    private InkUsageService service;

    @PostMapping
    public <T> ResponseEntity<List<T>> getAll(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated) {
        return createResponse(service.getAll(aggregated));
    }

    @PostMapping("/Period")
    public <T> ResponseEntity<List<T>> getAllForPeriod(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated, @RequestBody PeriodDto request) {
        return createResponse(service.getAllForPeriod(aggregated, request.getFrom(), request.getTo()));
    }

    @PostMapping("/Printer")
    public ResponseEntity<List<InkUsageDto>> getPrinters(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated, @RequestBody PrinterIdsDto request) {
        List<InkUsageDto> data
                = service.getPrinters(request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/PeriodAndPrinter")
    public ResponseEntity<List<InkUsageDto>> getPrintersForPeriod(@RequestParam(value = "aggregated", defaultValue = "true") boolean aggregated, @RequestBody PeriodAndPrinterIdsDto request) {
        List<InkUsageDto> data
                = service.getPrintersForPeriod(request.getFrom(), request.getTo(), request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/AvailableTimePeriod")
    public ResponseEntity<PeriodDto> getAvailableTimePeriod() {
        PeriodDto data = service.getAvailableTimePeriod();
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/AvailablePrinters")
    public ResponseEntity<PrinterIdsDto> getAvailablePrinters() {
        return createResponse(service.getAvailablePrinters());
    }

    private <T> ResponseEntity<List<T>> createResponse(List<T> data) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    private <T> ResponseEntity<T> createResponse(T data) {
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
