package api.coloradodashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PeriodAndPrinterRequest {
    private Date from;
    private Date to;
    private List<String> printerIds;
}
