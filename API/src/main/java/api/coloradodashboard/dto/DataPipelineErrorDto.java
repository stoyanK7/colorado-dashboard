package api.coloradodashboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataPipelineErrorDto {
    private Long id;
    private Boolean passed;
    private String step;
    private String affectedGraphs;
    private String location;
    private Date dateTime;
    private String log;

    public DataPipelineErrorDto(Boolean passed, String step,
                                Date dateTime) {
        this.passed = passed;
        this.step = step;
        this.dateTime = dateTime;
    }
}
