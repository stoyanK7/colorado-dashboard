package api.coloradodashboard.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Table(name = "data_pipeline_errors")
@Entity
public class DataPipelineErrorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean passed;
    private String step;
    private String affectedGraphs;
    private String location;
    private Date dateTime;
    @Column(columnDefinition = "TEXT")
    private String log;
}
