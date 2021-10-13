package com.example.canondashboardapi.model.models;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class DatesModel {
    Date startingDate;
    Date endingDate;
}
