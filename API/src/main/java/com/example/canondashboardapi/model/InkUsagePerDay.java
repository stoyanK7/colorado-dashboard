package com.example.canondashboardapi.model;

import com.example.canondashboardapi.enumeration.InkType;
import com.example.canondashboardapi.model.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InkUsagePerDay extends BaseModel {
    private Date date;
    private double totalInkUsed;
    private InkType inkType;
}
