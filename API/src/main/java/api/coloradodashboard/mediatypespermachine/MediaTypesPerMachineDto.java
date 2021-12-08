package api.coloradodashboard.mediatypespermachine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MediaTypesPerMachineDto {
    @JsonProperty("Media type")
    private String mediaType;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;
}
