package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.dto.DataPipelineErrorDto;
import api.coloradodashboard.repository.DataPipelineErrorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for Apache Airflow data pipeline errors.
 * Returns a DataPipelineErrorDto object or 404/NOT FOUND if no data is present.
 */
@AllArgsConstructor
@RequestMapping("DataPipelineError")
@RestController
public class DataPipelineErrorController
        implements BaseController {
    private DataPipelineErrorRepository repository;

    @GetMapping("/Latest")
    public ResponseEntity<DataPipelineErrorDto> getLatest() {
        return createResponse(repository.getLatest());
    }
}
