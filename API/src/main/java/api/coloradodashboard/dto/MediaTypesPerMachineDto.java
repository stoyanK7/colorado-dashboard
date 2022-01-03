package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaTypesPerMachineDto {
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Media type")
    private String mediaType;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;

    public MediaTypesPerMachineDto(String mediaType, Double printedSquareMeters) {
        this.mediaType = mediaType;
        this.printedSquareMeters = printedSquareMeters;
    }
}
