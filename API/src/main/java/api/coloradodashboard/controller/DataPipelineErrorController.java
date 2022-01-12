package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.dto.DataPipelineErrorDto;
import api.coloradodashboard.repository.DataPipelineErrorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("DataPipelineError")
@RestController
public class DataPipelineErrorController
        extends BaseController {
    private DataPipelineErrorRepository repository;

    @GetMapping("/Latest")
    public ResponseEntity<DataPipelineErrorDto> getLatest() {
        return createResponse(repository.getLatest());
    }
}
