package api.coloradodashboard.mediacategoryusage;

import api.coloradodashboard.interfaces.GenericGraphConverter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts a list of MediaCategoryUsage to a list of maps with the following format:
 * KEY                  | VALUE
 * ----------------------------------------------------
 * date                 | date in String (dd-mm-yyyy)
 * mediaCategoryName1   | amount of printed square meters in Double
 * mediaCategoryName2   | amount of printed square meters in Double
 * ...item for every MediaCategory
 * <p>
 * Every map in the returned list represents another day.
 */
@Component
public class MediaCategoryUsageConverter implements
        GenericGraphConverter<List<MediaCategoryUsage>, List<Map<String, String>>> {
    /**
     * Converts a list of MediaCategoryUsage objects
     * to a list of maps in the above specified format.
     *
     * @param objects List of MediaCategoryUsage
     *                objects spanning any amount of days and categories
     * @return List of Maps representing days
     */
    @Override
    public List<Map<String, String>> modelToDTO(List<MediaCategoryUsage> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<MediaCategory, Double>> intermediate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (MediaCategoryUsage mediaCategoryUsage : objects) {
            String date = dateFormat.format(mediaCategoryUsage.getDate());

            if (!intermediate.containsKey(date))
                intermediate.put(date, new HashMap<>());

            intermediate.get(date)
                    .put(mediaCategoryUsage.getMediaCategory(),
                            mediaCategoryUsage.getPrintedSquareMeters());
        }

        // Change intermediate to final list of hashmaps
        for (String date : intermediate.keySet()) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("date", date);

            for (MediaCategory mediaCategory : intermediate.get(date).keySet()) {
                newMap.put(mediaCategory.toString(),
                        intermediate.get(date).get(mediaCategory).toString());
            }
            result.add(newMap);
        }
        return result;
    }
}
