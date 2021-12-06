package api.coloradodashboard.mediatypespermachine;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>REST API</b> controller for <b><i>Media types per machine</i></b> chart.
 * Returns a list of MediaTypesPerMachineDto objects or <b>404</b> if no data is present.
 */
@RestController
@RequestMapping("MediaTypesPerMachine")
@CrossOrigin("http://localhost:4000")
@AllArgsConstructor
public class MediaTypesPerMachineController {
//    private MediaTypesPerMachineService service;

}
