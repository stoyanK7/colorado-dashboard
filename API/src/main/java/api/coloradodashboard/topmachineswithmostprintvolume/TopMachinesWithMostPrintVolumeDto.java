package api.coloradodashboard.topmachineswithmostprintvolume;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopMachinesWithMostPrintVolumeDto {
    private String printerId;
    private Double printedSquareMeters;
}
