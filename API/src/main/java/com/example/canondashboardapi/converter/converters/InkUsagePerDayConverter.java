package com.example.canondashboardapi.converter.converters;

import com.example.canondashboardapi.converter.interfaces.GenericGraphConverter;
import com.example.canondashboardapi.enumeration.InkType;
import com.example.canondashboardapi.model.InkUsagePerDay;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts a list of TotalPrintSquareMeterPerMediaCategoryPerDay to a list of maps with the following format:
 * KEY                      | VALUE
 * ----------------------------------------------------
 * date                     | date in String (dd-mm-yyyy)
 * accountedInkCyanMl       | amount of used ink in Double
 * accountedInkMagentaMl    |
 * accountedInkYellowMl     |
 * accountedInkBlackMl      |
 * <p>
 * Every map in the returned list represents a day.
 */
@Component
public class InkUsagePerDayConverter implements
        GenericGraphConverter<List<InkUsagePerDay>, List<Map<String, String>>> {
    /**
     * Converts a list of InkUsagePerDay objects to a list of maps in the above specified format.
     *
     * @param objects List of InkUsagePerDay objects spanning any amount of days and categories.
     * @return List of Maps representing days.
     */
    @Override
    public List<Map<String, String>> modelToDTO(List<InkUsagePerDay> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<InkType, Double>> intermediate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (InkUsagePerDay inkUsagePerDay : objects) {
            String date = dateFormat.format(inkUsagePerDay.getDate());
            if (!intermediate.containsKey(date))
                intermediate.put(date, new HashMap<>());
            intermediate.get(date)
                    .put(inkUsagePerDay.getInkType(),
                            inkUsagePerDay.getTotalInkUsed());
        }

        // Change intermediate to final list of hashmaps
        for (String date : intermediate.keySet()) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("date", date);
            for (InkType inkType : intermediate.get(date)
                    .keySet()) {
                newMap.put(inkType.toString(),
                        intermediate.get(date).get(inkType).toString());
            }
            result.add(newMap);
        }
        return result;
    }
}
