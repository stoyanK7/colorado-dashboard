package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class SquareMetersPerPrintModeDto {
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Max speed")
    private Double maxSpeedPrinted;
    @JsonProperty("High speed")
    private Double highSpeedPrinted;
    @JsonProperty("Production")
    private Double productionPrinted;
    @JsonProperty("High quality")
    private Double highQualityPrinted;
    @JsonProperty("Specialty")
    private Double specialtyPrinted;
    @JsonProperty("Backlit")
    private Double backlitPrinted;
    @JsonProperty("Reliance")
    private Double reliancePrinted;
    @JsonProperty("Other")
    private Double otherPrinted;
}
