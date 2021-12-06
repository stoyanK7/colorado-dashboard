package api.coloradodashboard.topmachineswithmostprintvolume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeDto {
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;
}
