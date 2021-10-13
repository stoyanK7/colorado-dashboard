package com.example.canondashboardapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintSquareMeterPerMediaCategoryDTO {

    private Date dateTime;
    private double printedSquareMeter;
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
