package com.example.canondashboardapi.Model.models;

import com.example.canondashboardapi.Enum.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPrintSquareMeterPerMediaCategoryPerDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date dateTime;
    private double totalPrintedSquareMeter;
    private MediaType mediaType;

    public long getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getTotalPrintedSquareMeter() {
        return totalPrintedSquareMeter;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
