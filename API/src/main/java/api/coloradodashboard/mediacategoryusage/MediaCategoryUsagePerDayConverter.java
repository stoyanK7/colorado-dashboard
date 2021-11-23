package api.coloradodashboard.mediacategoryusage;

import api.coloradodashboard.interfaces.GenericGraphConverter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts a list of TotalPrintSquareMeterPerMediaCategoryPerDay to a list of maps with the following format:
 * KEY                  | VALUE
 * ----------------------------------------------------
 * date                 | date in String (dd-mm-yyyy)
 * mediaCategoryName1   | amount of printed square meters in String
 * mediaCategoryName2   | amount of printed square meters in String
 * item for every MediaCategory
 * <p>
 * Every map in the returned list represents another day
 */
@Component
public class MediaCategoryUsagePerDayConverter implements
        GenericGraphConverter<List<MediaCategoryUsagePerDay>, List<Map<String, String>>> {
    /**
     * Converts a list of TotalPrintSquareMeterPerMediaCategoryPerDay objects
     * to a list of maps in the above specified format.
     *
     * @param objects List of TotalPrintSquareMeterPerMediaCategoryPerDay
     *                objects spanning any amount of days and categories
     * @return List of Maps representing days
     */
    @Override
    public List<Map<String, String>> modelToDTO(List<MediaCategoryUsagePerDay> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<MediaCategory, Double>> intermediate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (MediaCategoryUsagePerDay mediaCategoryUsagePerDay : objects) {
            String date = dateFormat.format(
                    mediaCategoryUsagePerDay.getDate());
            if (!intermediate.containsKey(date)) {
                intermediate.put(date, new HashMap<>());
            }
            intermediate.get(date)
                    .put(mediaCategoryUsagePerDay.getMediaCategory(),
                            mediaCategoryUsagePerDay.getTotalPrintedSquareMeter());
        }

        // Change intermediate to final list of hashmaps
        for (String date : intermediate.keySet()) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("date", date);
            for (MediaCategory mediaCategory : intermediate.get(date)
                    .keySet()) {
                newMap.put(mediaCategory.toString(),
                        intermediate.get(date).get(mediaCategory).toString());
            }
            result.add(newMap);
        }
        return result;
    }
}
