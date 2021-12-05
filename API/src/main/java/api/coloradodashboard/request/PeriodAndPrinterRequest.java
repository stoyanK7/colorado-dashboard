package api.coloradodashboard.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodAndPrinterRequest {
    private Date from;
    private Date to;
    private List<String> printerIds;
}
