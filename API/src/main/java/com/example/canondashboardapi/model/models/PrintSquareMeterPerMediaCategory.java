package com.example.canondashboardapi.model.models;

import com.example.canondashboardapi.model.interfaces.IPrintSquareMeterPerMediaCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class PrintSquareMeterPerMediaCategory implements IPrintSquareMeterPerMediaCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date dateTime;
    private double film;
    private double lightPaper;
    private double heavyPaper;
    private double lightBanner;
    private double textile;
    private double monomericVinyl;
    private double canvas;
    private double polymeric;
    private double heavyBanner;
    private double paper;
    private double thickFilm;
    
}
