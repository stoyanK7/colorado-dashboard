package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaCategoryUsageDto {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Media category")
    private String mediaCategory;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;

    public MediaCategoryUsageDto(String date,
                                 String mediaCategory, Double printedSquareMeters) {
        this.date = date;
        this.mediaCategory = mediaCategory;
        this.printedSquareMeters = printedSquareMeters;
    }
}
