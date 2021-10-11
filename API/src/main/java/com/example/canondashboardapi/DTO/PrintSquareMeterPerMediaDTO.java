package com.example.canondashboardapi.DTO;

import java.util.Date;

public class PrintSquareMeterPerMediaDTO {

    private Date dateTime;
    private double printedSquareMeter;
    private MediaType mediaType;

    public Date getDateTime() {
        return dateTime;
    }
    public double getPrintedSquareMeter() {
        return printedSquareMeter;
    }
    public MediaType getMediaType() {
        return mediaType;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public void setPrintedSquareMeter(double printedSquareMeter) {
        this.printedSquareMeter = printedSquareMeter;
    }
    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
