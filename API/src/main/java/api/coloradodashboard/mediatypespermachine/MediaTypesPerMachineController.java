package api.coloradodashboard.mediatypespermachine;

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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>REST API</b> controller for <b><i>Media types per machine</i></b> chart.
 * Returns a list of <b>MediaTypesPerMachineDto</b> objects or <b>404</b> if no data is present.
 */
@RestController
@RequestMapping("MediaTypesPerMachine")
@CrossOrigin("http://localhost:4000")
@AllArgsConstructor
public class MediaTypesPerMachineController {
    private MediaTypesPerMachineService service;

    /**
     * <b>GET</b> request returning all data from the database.
     *
     * @return A <b>list of MediaTypesPerMachineDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @GetMapping
    public ResponseEntity<List<MediaTypesPerMachineDto>> getAll() {
        List<MediaTypesPerMachineDto> data = service.getAll();
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
     * @return A <b>list of MediaTypesPerMachineDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @PostMapping("/Period")
    public ResponseEntity<List<MediaTypesPerMachineDto>> getAllForPeriod(@RequestBody PeriodDto request) {
        List<MediaTypesPerMachineDto> data
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
     *                "printerIds": [
     *                "702",
     *                "703
     *                ]
     *                }
     * @return A <b>list of MediaTypesPerMachineDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @PostMapping("/Printer")
    public ResponseEntity<List<MediaTypesPerMachineDto>> getPrinters(@RequestBody PrinterIdsDto request) {
        List<MediaTypesPerMachineDto> data
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
     *                "from": "2021-12-20",
     *                "to": "2021-12-30",
     *                "printerIds": [
     *                "702",
     *                "703
     *                ]
     *                }
     * @return A <b>list of MediaTypesPerMachineDto objects</b>, each
     * one representing a different printer, or <b>404</b> if no data is present.
     */
    @PostMapping("/PeriodAndPrinter")
    public ResponseEntity<List<MediaTypesPerMachineDto>> getPrintersForPeriod(@RequestBody PeriodAndPrinterIdsDto request) {
        List<MediaTypesPerMachineDto> data
                = service.getPrintersForPeriod(request.getFrom(), request.getTo(), request.getPrinterIds());
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * <b>GET</b> request returning the minimum and maximum date from the database
     * table.
     *
     * @return A <b>PeriodDto object</b>, containing the minimum and maximum possible
     * dates.
     */
    @GetMapping("/AvailableTimePeriod")
    public ResponseEntity<PeriodDto> getAvailableTimePeriod() {
        PeriodDto data = service.getAvailableTimePeriod();
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * <b>GET</b> request returning all available printers in the database table.
     *
     * @return A <b>PrinterIdsDto object</b>, containing a list of all available
     * printers.
     */
    @GetMapping("/AvailablePrinters")
    public ResponseEntity<PrinterIdsDto> getAvailablePrinters() {
        PrinterIdsDto data = service.getAvailablePrinters();
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
