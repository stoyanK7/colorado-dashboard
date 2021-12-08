package api.coloradodashboard.inkusage;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts a list of InkUsage to a list of maps with the following format:
 * KEY              | VALUE
 * ----------------------------------------------------
 * date             | date in String (dd-mm-yyyy)
 * cyan             | amount of used ink in Double
 * magenta          |
 * yellow           |
 * black            |
 * <p>
 * Every map in the returned list represents a day.
 */
@Component
public class InkUsageConverter {
    /**
     * Converts a list of InkUsage objects to a list of maps in the
     * above specified format.
     *
     * @param objects List of InkUsage objects spanning any amount
     *                of days and categories.
     * @return List of Maps representing days.
     */


    public List<Map<String, String>> modelToDTO(List<InkUsage> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<InkType, Double>> intermediate = new LinkedHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (InkUsage inkUsage : objects) {
            String date = dateFormat.format(inkUsage.getDate());

            if (!intermediate.containsKey(date))
                intermediate.put(date, new HashMap<>());

            intermediate.get(date)
                    .put(inkUsage.getInkType(),
                            inkUsage.getInkUsed());
        }

        // Change intermediate to final list of hashmaps
        for (String date : intermediate.keySet()) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("date", date);

            for (InkType inkType : intermediate.get(date).keySet()) {
                newMap.put(inkType.toString(),
                        intermediate.get(date).get(inkType).toString());
            }
            result.add(newMap);
        }
        return result;
    }
}
