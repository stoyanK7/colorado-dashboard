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
 * <b>REST API</b> controller for <b><i>Top machines with most print volume</i></b> chart.
 * Returns a list of TopMachinesWithMostPrintVolumeDto objects or 404 if no data is present.
 */
@RestController
@RequestMapping("TopMachinesWithMostPrintVolume")
@CrossOrigin("http://localhost:4000")
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeController {
    TopMachinesWithMostPrintVolumeService service;

    /**
     * <b>GET</b> request returning all data from the database.
     *
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @GetMapping
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getAll() {
        List<TopMachinesWithMostPrintVolumeDto> data = service.getAll();
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * <b>POST</b> request returning all data from the database for the provided
     * time period.
     *
     * @param request A <b>JSON object</b>, with two fields. Expected format:
     *                {
     *                "from": "2021-12-20",
     *                "to": "2021-12-30
     *                }
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @PostMapping("/Period")
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getAllForPeriod(@RequestBody PeriodRequest request) {
        List<TopMachinesWithMostPrintVolumeDto> data
                = service.getAllForPeriod(request.getFrom(), request.getTo());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * <b>POST</b> request returning all data from the database for the provided
     * list of printers.
     *
     * @param request A <b>JSON object</b>, with one field. Expected format:
     *                {
     *                  "printerIds": [
     *                      "702",
     *                      "703
     *                  ]
     *                }
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @PostMapping("/Printer")
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getPrinters(@RequestBody PrinterRequest request) {
        List<TopMachinesWithMostPrintVolumeDto> data
                = service.getPrinters(request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * <b>POST</b> request returning all data from the database for the provided
     * time period and list of printers.
     *
     * @param request A <b>JSON object</b>, with one field. Expected format:
     *                {
     *                  "from": "2021-12-20",
     *                  "to": "2021-12-30",
     *                  "printerIds": [
     *                      "702",
     *                      "703
     *                  ]
     *                }
     * @return A <b>list of TopMachinesWithMostPrintVolumeDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @PostMapping("/PeriodAndPrinter")
    public ResponseEntity<List<TopMachinesWithMostPrintVolumeDto>> getPrintersForPeriod(@RequestBody PeriodAndPrinterRequest request) {
        List<TopMachinesWithMostPrintVolumeDto> data
                = service.getPrintersForPeriod(request.getFrom(), request.getTo(), request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
