package api.coloradodashboard.mediacategoryusage;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

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
public class MediaCategoryUsageConverter {
    /**
     * Converts a list of MediaCategoryUsage objects
     * to a list of MediaCategoryUsageDTO-s in the above specified format.
     *
     * @param objects List of MediaCategoryUsage
     *                objects spanning any amount of days and categories
     * @return List of DTOS representing days
     */
    public List<MediaCategoryUsageDTO> modelToDTO(List<MediaCategoryUsage> objects) {

        if(objects.size() == 0){
            return null;
        }

        Date currentDate = null;
        List<MediaCategoryUsageDTO> resultList = new ArrayList<>();
        MediaCategoryUsageDTO dtoTemplate = new MediaCategoryUsageDTO();
        for (MediaCategoryUsage dataObject :objects) {

            if(currentDate != dataObject.getDate()){

                if(currentDate != null){
                    resultList.add(dtoTemplate);
                }
                currentDate = dataObject.getDate();
                dtoTemplate = new MediaCategoryUsageDTO();
            }

            if(dtoTemplate.getDayDate() == currentDate)
            {
                dtoTemplate.setDayDate(currentDate);
            }

            if (dataObject.getMediaCategory() == MediaCategory.CANVAS){
                dtoTemplate.setCanvas(dtoTemplate.getCanvas() + dataObject.getPrintedSquareMeters());
            }
            else if (dataObject.getMediaCategory() == MediaCategory.FILM){
                dtoTemplate.setFilm(dtoTemplate.getFilm() + dataObject.getPrintedSquareMeters());
            }
            else if (dataObject.getMediaCategory() == MediaCategory.HEAVY_BANNER){
                dtoTemplate.setHeavyBanner(dtoTemplate.getHeavyBanner() + dataObject.getPrintedSquareMeters());
            }
            else if (dataObject.getMediaCategory() == MediaCategory.PAPER){
                dtoTemplate.setPaper(dtoTemplate.getPaper() + dataObject.getPrintedSquareMeters());
            }
            else if (dataObject.getMediaCategory() == MediaCategory.TEXTILE){
                dtoTemplate.setTextile(dtoTemplate.getTextile() + dataObject.getPrintedSquareMeters());
            }
        }
        return resultList;
    }

//
//    List<Map<String, String>> result = new ArrayList<>();
//    Map<String, Map<MediaCategory, Double>> intermediate = new LinkedHashMap<>();
//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//        for (MediaCategoryUsage mediaCategoryUsage : objects) {
//        String date = dateFormat.format(mediaCategoryUsage.getDate());
//
//        if (!intermediate.containsKey(date))
//            intermediate.put(date, new HashMap<>());
//
//        intermediate.get(date)
//                .put(mediaCategoryUsage.getMediaCategory(),
//                        mediaCategoryUsage.getPrintedSquareMeters());
//    }
//
//    // Change intermediate to final list of hashmaps
//        for (String date : intermediate.keySet()) {
//        Map<String, String> newMap = new HashMap<>();
//        newMap.put("date", date);
//
//        for (MediaCategory mediaCategory : intermediate.get(date).keySet()) {
//            newMap.put(mediaCategory.toString(),
//                    intermediate.get(date).get(mediaCategory).toString());
//        }
//        result.add(newMap);
//    }
//        return result;
}
