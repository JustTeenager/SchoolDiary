package com.example.schooldiary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "diary")
public class DayItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int day;
    @Ignore
    private String date_title;
    private boolean isEven;

    public DayItem(int day,boolean isEven){
        this.day=day;
        this.isEven=isEven;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isEven() {
        return isEven;
    }

    public void setEven(boolean even) {
        isEven = even;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_title() {
        return date_title;
    }

    public void setDate_title(String date_title) {
        this.date_title = date_title;
    }
    //private String date;
    //
}
