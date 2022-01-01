package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeDto {
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;
}
