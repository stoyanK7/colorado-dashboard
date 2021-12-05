package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.PeriodAndPrinterRequest;
import api.coloradodashboard.PeriodRequest;
import api.coloradodashboard.PrinterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API controller for Ink Usage. Outgoing graph information is output
 * as a List of Maps, where very Map represents a bar in the graph.
 */
@RestController
@RequestMapping("TopMachinesWithMostPrintVolume")
@CrossOrigin("http://localhost:4000")
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeController {
    TopMachinesWithMostPrintVolumeService service;

    /**
     * GET request that returns all the data stored in the repository
     *
     * @return A List of Maps, representing all the days stored in the repository
     */
    @GetMapping
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getAll() {
        List<TopMachinesWithMostPrintVolumeDto> data = service.getAll();
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/Period")
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getAllForPeriod(@RequestBody PeriodRequest request) {
        List<TopMachinesWithMostPrintVolumeDto> data
                = service.getAllForPeriod(request.getFrom(), request.getTo());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/Printer")
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getPrinters(@RequestBody PrinterRequest request) {
        List<TopMachinesWithMostPrintVolumeDto> data
                = service.getPrinters(request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/PeriodAndPrinter")
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>>
    getPrintersForPeriod(@RequestBody PeriodAndPrinterRequest request) {
        List<TopMachinesWithMostPrintVolumeDto> data
                = service.getPrintersForPeriod(request.getFrom(), request.getTo(), request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
