package api.coloradodashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrinterIdsDto {
    private List<String> printerIds;
}
