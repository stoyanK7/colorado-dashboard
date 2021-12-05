package api.coloradodashboard.topmachineswithmostprintvolume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeDto {
    private String printerId;
    private Double printedSquareMeters;
}
