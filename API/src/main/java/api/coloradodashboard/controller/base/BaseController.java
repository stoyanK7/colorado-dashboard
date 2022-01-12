package api.coloradodashboard.controller.base;

import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class BaseController {
    /**
     * Creates a response entity based on whether the provided list is empty or not.
     *
     * @param data List of DTOs
     * @return ResponseEntity with status code 200/OK and the list of DTOs or 404/
     * NOT FOUND if the data list is empty.
     */
    public <D> ResponseEntity<List<D>> createResponse(List<D> data) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }

    /**
     * Creates a response entity based on whether the provided object is null or not.
     *
     * @param data Any object
     * @param <Y>  Any class. Usually PeriodDto, PrinterIdsDto or ChartDataKeysDto
     *             but any class can be used here.
     * @return ResponseEntity with status code 200/OK and the object or 404/NOT FOUND
     * if the object is null.
     */
    public <Y> ResponseEntity<Y> createResponse(Y data) {
        if (data == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(data);
    }
}
