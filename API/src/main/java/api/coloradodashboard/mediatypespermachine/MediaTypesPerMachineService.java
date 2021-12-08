package api.coloradodashboard.mediatypespermachine;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for <b>Media types per machine</b>. Returns <b>lists of
 * TopMachinesWithMostPrintVolumeDto</b> objects depending on provided criteria.
 */
@Service
@AllArgsConstructor
public class MediaTypesPerMachineService {
    public List<MediaTypesPerMachineDto> getAll() {
        return new ArrayList<MediaTypesPerMachineDto>();
    }

    public List<MediaTypesPerMachineDto> getAllForPeriod(Date from, Date to) {
        return new ArrayList<MediaTypesPerMachineDto>();

    }

    public List<MediaTypesPerMachineDto> getPrinters(List<String> printerIds) {
        return new ArrayList<MediaTypesPerMachineDto>();

    }

    public List<MediaTypesPerMachineDto> getPrintersForPeriod(Date from, Date to, List<String> printerIds) {
        return new ArrayList<MediaTypesPerMachineDto>();

    }
}
