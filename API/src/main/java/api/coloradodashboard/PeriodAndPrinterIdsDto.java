package api.coloradodashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodAndPrinterIdsDto {
    private Date from;
    private Date to;
    private List<String> printerIds;
}
