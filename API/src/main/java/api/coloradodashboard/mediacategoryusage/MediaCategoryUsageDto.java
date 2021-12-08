package api.coloradodashboard.mediacategoryusage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MediaCategoryUsageDto {
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Film")
    private Double filmUsed;
    @JsonProperty("Light paper < 120gsm")
    private Double lightPaperUsed;
    @JsonProperty("Heavy paper > 200gsm")
    private Double heavyPaperUsed;
    @JsonProperty("Light banner < 400gsm")
    private Double lightBannerUsed;
    @JsonProperty("Textile")
    private Double textileUsed;
    @JsonProperty("Monomeric vinyl")
    private Double monomericVinylUsed;
    @JsonProperty("Canvas")
    private Double canvasUsed;
    @JsonProperty("Polymeric & cast vinyl")
    private Double polymericAndCastVinylUsed;
    @JsonProperty("Heavy banner > 400gsm")
    private Double heavyBannerUsed;
    @JsonProperty("Paper")
    private Double paperUsed;
    @JsonProperty("Thick film > 200um")
    private Double thickFilmUsed;
}
