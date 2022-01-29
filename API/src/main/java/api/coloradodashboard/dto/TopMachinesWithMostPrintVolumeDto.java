package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopMachinesWithMostPrintVolumeDto {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;

    public TopMachinesWithMostPrintVolumeDto(String printerId, Double printedSquareMeters) {
        this.printerId = printerId;
        this.printedSquareMeters = printedSquareMeters;
    }
}
