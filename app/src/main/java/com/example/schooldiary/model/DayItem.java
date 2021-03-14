package com.example.schooldiary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary")
public class    DayItem {

    @NonNull
    @PrimaryKey
    private String day;

    public DayItem(String day){
        this.day=day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    //private String date;
    //private List<TableItem> subjects;
}
