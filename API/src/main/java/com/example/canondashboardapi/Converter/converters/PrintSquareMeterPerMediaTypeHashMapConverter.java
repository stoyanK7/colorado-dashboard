package com.example.canondashboardapi.Converter.converters;

import com.example.canondashboardapi.Converter.interfaces.IGraphConverter;
import com.example.canondashboardapi.Enum.MediaType;
import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import com.example.canondashboardapi.Model.models.TotalPrintSquareMeterPerMediaCategoryPerDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts a list of PrintSquareMeterPerMediaType to a list of maps with the following format:
 * Key                  | Value
 * date                 | date in String (dd-mm-yyyy)
 * mediaCategoryName1   | amount of printed square meters in String
 * mediaCategoryName2   | amount of printed square meters in String
 * idem for every mediaCategory
 *
 * Every map in the returned list represents another day
 */
public class PrintSquareMeterPerMediaTypeHashMapConverter implements IGraphConverter<List<TotalPrintSquareMeterPerMediaCategoryPerDay>, List<Map<String, String>>> {

    @Override
    public List<Map<String, String>> modelToDTO(List<TotalPrintSquareMeterPerMediaCategoryPerDay> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<MediaType, Double>> intermediate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (TotalPrintSquareMeterPerMediaCategoryPerDay TotalPrintSquareMeterPerMediaCategoryPerDay : objects) {
            String date = dateFormat.format(TotalPrintSquareMeterPerMediaCategoryPerDay.getDateTime());
            if (!intermediate.containsKey(date)) {
                intermediate.put(date, new HashMap<>());
            }
            intermediate.get(date).put(TotalPrintSquareMeterPerMediaCategoryPerDay.getMediaType(), TotalPrintSquareMeterPerMediaCategoryPerDay.getTotalPrintedSquareMeter());
        }

        // Change intermediate to final list of hashmaps
        for (String date : intermediate.keySet()) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("date", date);
            for (MediaType mediaType : intermediate.get(date).keySet()) {
                newMap.put(mediaType.toString(), intermediate.get(date).get(mediaType).toString());
            }

            result.add(newMap);
        }

        return result;
    }

    // TODO does this method even make sense? Maybe change interface
    @Override
    public List<List<Map<String, String>>> ListModelToDTO(List<List<TotalPrintSquareMeterPerMediaCategoryPerDay>> lists) {
        return null;
    }
}
