package api.coloradodashboard.inkusage;

import api.coloradodashboard.interfaces.GenericGraphConverter;
import api.coloradodashboard.interfaces.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST api controller for Ink Usage. Outgoing graph information is output as
 * a List of Maps, where very Map represents a bar in the graph.
 */
@RestController
@RequestMapping("InkUsage")
@CrossOrigin("http://localhost:4000")
@AllArgsConstructor
public class InkUsageController {
    GenericGraphConverter<List<InkUsage>, List<Map<String, String>>>
            graphConverter;

    GenericService<InkUsage> service;

    /**
     * GET request that returns all the data stored in the repository
     *
     * @return A List of Maps, representing all the days stored in the repository
     */
    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getAll() {
        List<Map<String, String>> graphDayBars =
                graphConverter.modelToDTO(service.getAll());
        if (graphDayBars != null) return ResponseEntity.ok().body(graphDayBars);
        return new ResponseEntity("No data found.", HttpStatus.NOT_FOUND);
    }
}
