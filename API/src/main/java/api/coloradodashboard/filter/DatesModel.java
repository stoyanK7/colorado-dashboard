package api.coloradodashboard.filter;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class DatesModel {
    private Date startingDate;
    private Date endingDate;
}
