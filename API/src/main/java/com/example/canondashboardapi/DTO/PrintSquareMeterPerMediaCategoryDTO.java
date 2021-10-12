package com.example.canondashboardapi.DTO;

import java.util.Date;

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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getPrintedSquareMeter() {
        return printedSquareMeter;
    }

    public void setPrintedSquareMeter(double printedSquareMeter) {
        this.printedSquareMeter = printedSquareMeter;
    }

    public double getFilm() {
        return film;
    }

    public void setFilm(double film) {
        this.film = film;
    }

    public double getLightPaper() {
        return lightPaper;
    }

    public void setLightPaper(double lightPaper) {
        this.lightPaper = lightPaper;
    }

    public double getHeavyPaper() {
        return heavyPaper;
    }

    public void setHeavyPaper(double heavyPaper) {
        this.heavyPaper = heavyPaper;
    }

    public double getLightBanner() {
        return lightBanner;
    }

    public void setLightBanner(double lightBanner) {
        this.lightBanner = lightBanner;
    }

    public double getTextile() {
        return textile;
    }

    public void setTextile(double textile) {
        this.textile = textile;
    }

    public double getMonomericVinyl() {
        return monomericVinyl;
    }

    public void setMonomericVinyl(double monomericVinyl) {
        this.monomericVinyl = monomericVinyl;
    }

    public double getCanvas() {
        return canvas;
    }

    public void setCanvas(double canvas) {
        this.canvas = canvas;
    }

    public double getPolymeric() {
        return polymeric;
    }

    public void setPolymeric(double polymeric) {
        this.polymeric = polymeric;
    }

    public double getHeavyBanner() {
        return heavyBanner;
    }

    public void setHeavyBanner(double heavyBanner) {
        this.heavyBanner = heavyBanner;
    }

    public double getPaper() {
        return paper;
    }

    public void setPaper(double paper) {
        this.paper = paper;
    }

    public double getThickFilm() {
        return thickFilm;
    }

    public void setThickFilm(double thickFilm) {
        this.thickFilm = thickFilm;
    }
}
