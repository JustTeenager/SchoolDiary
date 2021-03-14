package com.example.schooldiary.Model;

import androidx.room.Entity;

import java.util.ArrayList;

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
