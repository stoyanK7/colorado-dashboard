package api.coloradodashboard.inkusage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InkUsageDto {
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Printer id")
    private String printerId;
    @JsonProperty("Cyan")
    private Double cyanLitresUsed;
    @JsonProperty("Magenta")
    private Double magentaLitresUsed;
    @JsonProperty("Yellow")
    private Double yellowLitresUsed;
    @JsonProperty("Black")
    private Double blackLitresUsed;

    public InkUsageDto(Date date,
                       Double cyanLitresUsed, Double magentaLitresUsed,
                       Double yellowLitresUsed, Double blackLitresUsed) {
        this.date = date;
        this.cyanLitresUsed = cyanLitresUsed;
        this.magentaLitresUsed = magentaLitresUsed;
        this.yellowLitresUsed = yellowLitresUsed;
        this.blackLitresUsed = blackLitresUsed;
    }
}
