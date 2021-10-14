package com.example.canondashboardapi.converter.converters;

import com.example.canondashboardapi.converter.interfaces.GenericGraphConverter;
import com.example.canondashboardapi.enumeration.MediaCategory;
import com.example.canondashboardapi.model.TotalPrintSquareMeterPerMediaCategoryPerDay;
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
 * idem for every MediaCategory
 *
 * Every map in the returned list represents another day
 */
@Component
public class PrintSquareMeterPerMediaCategoryHashMapConverter implements
        GenericGraphConverter<List<TotalPrintSquareMeterPerMediaCategoryPerDay>, List<Map<String, String>>> {

    /**
     * Converts a list of TotalPrintSquareMeterPerMediaCategoryPerDay objects
     * to a list of maps in the above specified format.
     *
     * @param objects List of TotalPrintSquareMeterPerMediaCategoryPerDay
     *                objects spanning any amount of days and categories
     * @return List of Maps representing days
     */
    @Override
    public List<Map<String, String>> modelToDTO(
            List<TotalPrintSquareMeterPerMediaCategoryPerDay> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<MediaCategory, Double>> intermediate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (TotalPrintSquareMeterPerMediaCategoryPerDay TotalPrintSquareMeterPerMediaCategoryPerDay : objects) {
            String date = dateFormat.format(
                    TotalPrintSquareMeterPerMediaCategoryPerDay.getDateTime());
            if (!intermediate.containsKey(date)) {
                intermediate.put(date, new HashMap<>());
            }
            intermediate.get(date)
                    .put(TotalPrintSquareMeterPerMediaCategoryPerDay.getMediaCategory(),
                            TotalPrintSquareMeterPerMediaCategoryPerDay.getTotalPrintedSquareMeter());
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
