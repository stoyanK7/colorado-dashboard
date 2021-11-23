package api.coloradodashboard.squaremeterperprintmode;

import api.coloradodashboard.interfaces.GenericGraphConverter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts a list of SquareMeterPerPrintMode to a list of maps with
 * the following format:
 * KEY                          | VALUE
 * ----------------------------------------------------
 * date                         | date in String (dd-mm-yyyy)
 * squareDecimeterArea          | amount of area printed in Double
 * printMode                    | print mode in String
 * <p>
 * Every map in the returned list represents a day.
 */
@Component
public class SquareMeterPerPrintModePerDayConverter implements
        GenericGraphConverter<List<SquareMeterPerPrintModePerDay>, List<Map<String, String>>> {
    /**
     * Converts a list of SquareMeterPerPrintModePerDay objects to a list
     * of maps in the above specified format.
     *
     * @param objects List of InkUsagePerDay objects spanning any amount
     *                of days and categories.
     * @return List of Maps representing days.
     */
    @Override
    public List<Map<String, String>> modelToDTO(
            List<SquareMeterPerPrintModePerDay> objects) {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, Map<PrintMode, Double>> intermediate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (SquareMeterPerPrintModePerDay squareMeterPerPrintModePerDay : objects) {
            String date = dateFormat.format(squareMeterPerPrintModePerDay.getDate());

            if (!intermediate.containsKey(date))
                intermediate.put(date, new HashMap<>());

            intermediate.get(date)
                    .put(squareMeterPerPrintModePerDay.getPrintMode(),
                            squareMeterPerPrintModePerDay.getSquareDecimeterArea());
        }

        // Change intermediate to final list of hashmaps
        for (String date : intermediate.keySet()) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("date", date);

            for (PrintMode printMode : intermediate.get(date).keySet()) {
                newMap.put(printMode.toString(),
                        intermediate.get(date).get(printMode).toString());
            }
            result.add(newMap);
        }
        return result;
    }
}
