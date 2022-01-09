package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SquareMetersPerPrintModeDto {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Print mode")
    private String printMode;
    @JsonProperty("Printed square meters")
    private Double printedSquareMeters;

    public SquareMetersPerPrintModeDto(String date, String printMode,
                                       Double printedSquareMeters) {
        this.date = date;
        this.printMode = printMode;
        this.printedSquareMeters = printedSquareMeters;
    }
}
