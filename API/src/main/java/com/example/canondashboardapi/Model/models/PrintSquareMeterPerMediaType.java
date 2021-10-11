package com.example.canondashboardapi.Model.models;

import com.example.canondashboardapi.Enum.MediaType;
import com.example.canondashboardapi.Model.interfaces.IPrintSquareMeterPerMediaType;

import java.util.Date;

public class PrintSquareMeterPerMediaType implements IPrintSquareMeterPerMediaType {

    private Date dateTime;
    private double printedSquareMeter;
    private MediaType mediaType;

    public PrintSquareMeterPerMediaType(Date dateTime, double printedSquareMeter, MediaType mediaType){
        this.dateTime = dateTime;
        this.printedSquareMeter = printedSquareMeter;
        this.mediaType = mediaType;
    }

    public Date getDateTime() {
        return dateTime;
    }
    public double getPrintedSquareMeter() {
        return printedSquareMeter;
    }
    public MediaType getMediaType() {
        return mediaType;
    }

}
