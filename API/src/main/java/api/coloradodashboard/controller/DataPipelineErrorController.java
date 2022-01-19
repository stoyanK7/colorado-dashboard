package api.coloradodashboard.controller;

import api.coloradodashboard.controller.base.BaseController;
import api.coloradodashboard.dto.DataPipelineErrorDto;
import api.coloradodashboard.repository.DataPipelineErrorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API controller for Apache Airflow data pipeline errors.
 * Returns a DataPipelineErrorDto object or 404/NOT FOUND if no data is present.
 */
@AllArgsConstructor
@RequestMapping("DataPipelineErrors")
@RestController
public class DataPipelineErrorController
        implements BaseController {
    private DataPipelineErrorRepository repository;

    @GetMapping("/Latest")
    public ResponseEntity<DataPipelineErrorDto> getLatest() {
        return createResponse(repository.getLatest(PageRequest.of(0, 1)).get(0));
    }

    @GetMapping
    public ResponseEntity<List<DataPipelineErrorDto>> getBy5(@RequestParam("page") int page) {
        return createResponse(repository.getAllByDateTimeDesc(PageRequest.of(page, 5)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataPipelineErrorDto> getById(@PathVariable("id") Long id) {
        return createResponse(repository.getOneById(id));
    }
}
