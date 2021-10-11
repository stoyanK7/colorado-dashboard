package com.example.canondashboardapi.Model.models;

import com.example.canondashboardapi.Enum.MediaType;
import com.example.canondashboardapi.Model.interfaces.IPrintSquareMeterPerMediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class PrintSquareMeterPerMediaType implements IPrintSquareMeterPerMediaType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date dateTime;
    private double printedSquareMeter;
    private MediaType mediaType;


}
