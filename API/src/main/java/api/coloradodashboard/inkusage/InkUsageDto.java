package api.coloradodashboard.inkusage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class InkUsageDto {
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Cyan")
    private Double cyanUsed;
    @JsonProperty("Magenta")
    private Double magentaUsed;
    @JsonProperty("Yellow")
    private Double yellowUsed;
    @JsonProperty("Black")
    private Double blackUsed;
}
