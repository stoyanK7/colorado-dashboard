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
public class TopMachinesWithMostPrintVolumeDto {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;

    public TopMachinesWithMostPrintVolumeDto(String date, Double printedSquareMeters) {
        this.date = date;
        this.printedSquareMeters = printedSquareMeters;
    }
}
