package api.coloradodashboard.filter;

import api.coloradodashboard.interfaces.FiltrationService;
import api.coloradodashboard.mediacategoryusage.MediaCategoryUsagePerDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("filtration")
@CrossOrigin("http://localhost:4000")
public class FilterController {
    @Autowired
    private FiltrationService service;

    /**
     * Retrieves data for the timeframe
     * @param model
     * @return
     */
    @GetMapping("byDates")
    public List<MediaCategoryUsagePerDay> getDataByDates(@RequestBody DatesModel model){
        
        return service.getDataByDates(model.getStartingDate(), model.getEndingDate());
    }
}
