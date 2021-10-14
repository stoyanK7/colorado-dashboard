package com.example.canondashboardapi.model;

import com.example.canondashboardapi.enumeration.MediaCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Represents data of the total number of square meters of a specific media
 * category that was printed on a single day.
 */
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
    private MediaCategory mediaCategory;
}
