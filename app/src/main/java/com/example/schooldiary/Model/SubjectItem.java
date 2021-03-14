package com.example.schooldiary.Model;

import androidx.room.Entity;

@Entity(tableName = "Subjects")
public class SubjectItem {
    private String name;
    private String notes;
    private String cab;
    private Type type;
    //сюда же добавим учителя
}
