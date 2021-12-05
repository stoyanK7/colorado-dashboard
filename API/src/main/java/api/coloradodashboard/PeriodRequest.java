package api.coloradodashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PeriodRequest {
    private Date from;
    private Date to;
}
