package com.example.schooldiary.model;

import androidx.room.Entity;

@Entity(tableName = "Diary")
public class DayItem {

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
