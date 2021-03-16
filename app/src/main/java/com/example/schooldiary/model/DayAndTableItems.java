package com.example.schooldiary.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DayAndTableItems {

    @Embedded
    private DayItem dayItem;

    @Relation(parentColumn = "day",entityColumn = "dayOfWeek")
    private List<TableItem> subjects;

    public DayItem getDayItem() {
        return dayItem;
    }

    public void setDayItem(DayItem dayItem) {
        this.dayItem = dayItem;
    }

    public List<TableItem> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TableItem> subjects) {
        this.subjects = subjects;
    }
}
