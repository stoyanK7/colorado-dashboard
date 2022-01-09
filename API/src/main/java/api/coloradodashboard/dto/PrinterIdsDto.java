package api.coloradodashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PrinterIdsDto {
    private List<String> printerIds;
}
